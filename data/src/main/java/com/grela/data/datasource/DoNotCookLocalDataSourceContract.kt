package com.grela.data.datasource

import com.grela.domain.DataResult
import com.grela.domain.model.ProfileGeneralModel

interface DoNotCookLocalDataSourceContract {
    fun saveProfile(profile: ProfileGeneralModel)
    fun getProfile(): DataResult<Error, ProfileGeneralModel>
    fun deleteProfile()
}