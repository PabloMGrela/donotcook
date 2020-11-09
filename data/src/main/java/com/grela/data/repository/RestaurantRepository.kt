package com.grela.data.repository

import com.grela.data.datasource.DoNotCookLocalDataSourceContract
import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import com.grela.data.model.toRestaurantDomainEntityList
import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.ProfileGeneralModel
import com.grela.domain.model.RestaurantDomainEntity

class RestaurantRepository(
    private val doNotCookRemoteDataSource: DoNotCookRemoteDataSourceContract,
    private val doNotCookLocalDataSource: DoNotCookLocalDataSourceContract
) : RestaurantRepositoryContract {
    override fun getRestaurants(): DataResult<Error, List<RestaurantDomainEntity>> {
        val localProfile = doNotCookLocalDataSource.getProfile()
        val token = if (localProfile is DataResult.Success) localProfile.r.token else ""
        return when (val result = doNotCookRemoteDataSource.getRestaurants(token)) {
            is DataResult.Error -> result
            is DataResult.Success -> DataResult.Success(result.r.toRestaurantDomainEntityList())
        }
    }

    override fun login(userName: String, password: String): DataResult<Error, ProfileGeneralModel> {
        return when (val result = doNotCookRemoteDataSource.login(userName, password)) {
            is DataResult.Error -> result
            is DataResult.Success -> {
                doNotCookLocalDataSource.saveProfile(result.r)
                result
            }
        }
    }

    override fun getUser(): DataResult<Error, ProfileGeneralModel> {
        return doNotCookLocalDataSource.getProfile()
    }

    override fun logout() {
        doNotCookLocalDataSource.deleteProfile()
    }

    override fun getOwnRestaurants(): DataResult<Error, List<RestaurantDomainEntity>> {
        val localProfile = doNotCookLocalDataSource.getProfile()
        val token = if (localProfile is DataResult.Success) localProfile.r.token else ""
        return when (val result = doNotCookRemoteDataSource.getRestaurants(token)) {
            is DataResult.Error -> result
            is DataResult.Success -> DataResult.Success(result.r.filter { it.isMine }
                .toRestaurantDomainEntityList())
        }
    }
}