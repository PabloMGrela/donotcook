package com.grela.data.datasource

import com.grela.domain.model.ProfileGeneralModel

interface DoNotCookLocalDataSourceContract {
    fun saveProfile(profile: ProfileGeneralModel)
}