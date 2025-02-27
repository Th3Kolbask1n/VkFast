package com.alexp.vkfast.presentation.main

sealed  class AuthState {

    object Authorized : AuthState()

    object NotAuthorized : AuthState()

    object Initial: AuthState()
}