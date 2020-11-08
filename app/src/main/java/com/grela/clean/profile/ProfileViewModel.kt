package com.grela.clean.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grela.domain.DataResult
import com.grela.domain.interactor.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val context: Context
) : ViewModel() {

    companion object {
        const val IS_AUTHENTICATED = "isAuthenticated"
    }

    private val _profileStatus = MutableLiveData<Profile>()
    val profileStatus: LiveData<Profile> = _profileStatus

    fun onViewCreated() {
        val isLoggedIn =
            context.getSharedPreferences(IS_AUTHENTICATED, Context.MODE_PRIVATE)
                .getBoolean(IS_AUTHENTICATED, false)
        if (isLoggedIn) setLoginStatus() else setLogoutStatus()

    }

    fun onLoginClicked(user: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.execute(user, pass)
            if (result is DataResult.Success) {
                setLoginStatus()
            } else {
                setLogoutStatus()
            }

        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            //logout things
        }
        setLogoutStatus()
    }

    private fun setLoginStatus() {
        context.getSharedPreferences(IS_AUTHENTICATED, Context.MODE_PRIVATE).edit()
            .putBoolean(
                IS_AUTHENTICATED, true
            ).apply()
        _profileStatus.postValue(Profile(ProfileStatus.LOGGED_IN))
    }

    private fun setLogoutStatus() {
        context.getSharedPreferences(IS_AUTHENTICATED, Context.MODE_PRIVATE).edit()
            .putBoolean(
                IS_AUTHENTICATED, false
            ).apply()
        _profileStatus.postValue(
            Profile((ProfileStatus.LOGGED_OUT))
        )
    }

}