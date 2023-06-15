package com.example.horizona

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    lateinit var navigateTo: (String) -> Unit

    fun showSignUp() {
        navigateTo("signUp")
    }

    fun showLogin() {
        navigateTo("login")
    }

    fun signUp() {
        navigateTo("verify")
    }

    fun verifyCode() {
        navigateTo("login")
    }

    fun login() {
        navigateTo("session")
    }

    fun logOut() {
        navigateTo("login")
    }
}