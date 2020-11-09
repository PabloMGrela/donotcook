package com.grela.clean.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grela.domain.DataResult
import com.grela.domain.interactor.profile.GetUserUseCase
import com.grela.domain.interactor.profile.LoginUseCase
import com.grela.domain.interactor.profile.LogoutUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUseCase: LoginUseCase,
    private val getProfile: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        const val IS_AUTHENTICATED = "isAuthenticated"
    }

    private val _profileStatus = MutableLiveData<Profile>()
    val profileStatus: LiveData<Profile> = _profileStatus

    fun onViewCreated() {
        viewModelScope.launch(dispatcher) {
            val result = getProfile.execute()
            if (result is DataResult.Success) {
                setLoginStatus(result.r.userName)
            } else {
                setLogoutStatus()
            }
        }
    }

    fun onLoginClicked(user: String, pass: String) {
        viewModelScope.launch(dispatcher) {
            val result = loginUseCase.execute(user, pass)
            if (result is DataResult.Success) {
                setLoginStatus(result.r.userName)
            } else {
                setLogoutStatus()
            }

        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            logoutUseCase.execute()
        }
        setLogoutStatus()
    }

    private fun setLoginStatus(name: String) {
        context.getSharedPreferences(IS_AUTHENTICATED, Context.MODE_PRIVATE).edit()
            .putBoolean(
                IS_AUTHENTICATED, true
            ).apply()
        _profileStatus.postValue(Profile(ProfileStatus.LOGGED_IN, name))
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