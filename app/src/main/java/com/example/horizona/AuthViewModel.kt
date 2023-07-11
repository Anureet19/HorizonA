package com.example.horizona

import android.location.Address
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    lateinit var navigateTo: (String) -> Unit

    var loginState = mutableStateOf(LoginState())
        private set

    var signUpState = mutableStateOf(SignUpState())
        private set

    var verificationCodeState = mutableStateOf(VerificationCodeState())
        private set

    fun updateSignUpState(fullName: String? = null, address: String?, phoneNumber: Number?, email: String? = null, password: String? = null) {
        fullName?.let { signUpState.value = signUpState.value.copy(fullName = it) }
        address?.let { signUpState.value = signUpState.value.copy(address = it) }
        phoneNumber?.let { signUpState.value = signUpState.value.copy(phoneNumber = it) }
        email?.let { signUpState.value = signUpState.value.copy(email = it)
            verificationCodeState.value = verificationCodeState.value.copy(email = it) }
        password?.let { signUpState.value = signUpState.value.copy(password = it) }
    }

    fun updateLoginState(email: String? = null, password: String? = null) {
        email?.let { loginState.value = loginState.value.copy(email = it) }
        password?.let { loginState.value = loginState.value.copy(password = it) }
    }

    fun updateVerificationCodeState(code: String) {
        verificationCodeState.value = verificationCodeState.value.copy(code = code)
    }

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