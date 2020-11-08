package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName

data class LoginRemoteEntity(
    @SerializedName("identifier") val userName: String,
    @SerializedName("password") val pass: String
)