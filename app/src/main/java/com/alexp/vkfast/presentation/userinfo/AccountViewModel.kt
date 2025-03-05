package com.alexp.vkfast.presentation.userinfo

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.vkfast.domain.entity.UserInfo
import com.alexp.vkfast.domain.usecases.GetProfileInfoUseCase
import com.alexp.vkfast.domain.usecases.GetRecommendationsUseCase
import com.vk.api.sdk.VK
import com.vk.id.VKID
import com.vk.id.logout.VKIDLogoutCallback
import com.vk.id.logout.VKIDLogoutFail
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val getUserInfoUseCase: GetProfileInfoUseCase,
) : ViewModel() {
    private val _uiState = mutableStateOf<ProfileState>(ProfileState.Loading)
    val uiState: State<ProfileState> = _uiState

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val userInfoFlow = getUserInfoUseCase()

                userInfoFlow.collect { userInfo ->
                    userInfo?.let {
                        _uiState.value = ProfileState.Success(it)
                    } ?: run {
                        _uiState.value = ProfileState.Error("Profile data is null")
                    }
                }

            } catch (e: Exception) {
                _uiState.value = ProfileState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            try {
                VKID.instance.logout(
                    callback = object : VKIDLogoutCallback {
                        override fun onSuccess() {
                        }

                        override fun onFail(fail: VKIDLogoutFail) {
                            when (fail) {
                                is VKIDLogoutFail.FailedApiCall -> fail.description
                                is VKIDLogoutFail.NotAuthenticated -> fail.description
                                is VKIDLogoutFail.AccessTokenTokenExpired -> fail
                            }
                        }
                    }
                )
            } catch (e: Exception) {
                _uiState.value = ProfileState.Error(e.message ?: "Ошибка выхода")

            }
        }
    }

    fun switchProfile() {
        VK.logout()
    }

    sealed class ProfileState {
        object Loading : ProfileState()
        data class Success(val profile: UserInfo) : ProfileState()
        data class Error(val message: String) : ProfileState()
    }
}