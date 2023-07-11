package com.example.horizona

import android.content.Context
import android.location.Address
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AmplifyServiceImpl()

    lateinit var navigateTo: (String) -> Unit

    var loginState = mutableStateOf(LoginState())
        private set

    var signUpState = mutableStateOf(SignUpState())
        private set

    var verificationCodeState = mutableStateOf(VerificationCodeState())
        private set

    fun updateSignUpState(fullName: String? = null, address: String? = null, phoneNumber: String? = null, email: String? = null, password: String? = null) {
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

    fun configureAmplify(context: Context){
        amplifyService.configureAmplify(context)
    }

    fun showSignUp() {
        navigateTo("signUp")
    }

    fun showLogin() {
        navigateTo("login")
    }

    fun signUp() {
        amplifyService.signUp(signUpState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("verify")
            }
        }
    }

    fun verifyCode() {
        amplifyService.verifyCode(verificationCodeState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }

    fun login() {
        amplifyService.login(loginState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("session")
            }
        }
    }

    fun logOut() {
        amplifyService.logOut {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }
}