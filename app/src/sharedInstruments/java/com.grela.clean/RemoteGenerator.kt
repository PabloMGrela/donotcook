package com.grela.clean

import com.grela.clean.MockGenerator.createList
import com.grela.clean.MockGenerator.givenACountryRemoteEntity
import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import com.grela.data.model.CountryDataEntity
import com.grela.domain.DataResult
import com.grela.remote_datasource.model.toDataEntity

object RemoteGenerator {

    fun givenARemoteSportDataSource(
        country: List<CountryDataEntity> = createList(3) { givenACountryRemoteEntity().toDataEntity() }
    ) = object : DoNotCookRemoteDataSourceContract {
        override fun getRestaurants(): DataResult<Error, List<CountryDataEntity>> {
            return if (country.isEmpty()) {
                DataResult.Error(Error())
            } else {
                DataResult.Success(country)
            }
        }
    }
}