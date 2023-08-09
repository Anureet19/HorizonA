package com.example.horizona

import android.content.Context
import android.location.Address
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AmplifyServiceImpl()

    lateinit var navigateTo: (String) -> Unit

    var selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
        private set

    var loginState = mutableStateOf(LoginState())
        private set

    var signUpState = mutableStateOf(SignUpState())
        private set

    var verificationCodeState = mutableStateOf(VerificationCodeState())
        private set

    fun updateSignUpState(fullName: String? = null, address: String? = null, phoneNumber: String? = null, username: String? = null, email: String? = null, password: String? = null) {
        fullName?.let { signUpState.value = signUpState.value.copy(fullName = it) }
        address?.let { signUpState.value = signUpState.value.copy(address = it) }
        phoneNumber?.let { signUpState.value = signUpState.value.copy(phoneNumber = it) }
        username?.let { signUpState.value = signUpState.value.copy(username = it)
            verificationCodeState.value = verificationCodeState.value.copy(username = it) }
        email?.let { signUpState.value = signUpState.value.copy(email = it) }
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

    fun showDashboard() {
        navigateTo("dashboard")
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

    var errorMessage = mutableStateOf<String?>(null)
        private set
    fun login() {
        errorMessage.value = null

        val email = loginState.value.email
        val password = loginState.value.password

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            errorMessage.value = "Please enter both email and password."
            return
        }
        try {
            amplifyService.login(loginState.value) {
                viewModelScope.launch(Dispatchers.Main) {
                    navigateTo("session")
                    // Reset the loginState values to clear the text fields
                    loginState.value = LoginState()
                }
            }
        }catch (e: AuthException) {
            // Handle AuthException and set appropriate error message
            when (e) {
                is AuthException.UserNotFoundException -> {
                    errorMessage.value = "User not found. Please check your email and password."
                }
                is AuthException.InvalidPasswordException -> {
                    errorMessage.value = "Invalid password. Please check your password."
                }
                else -> {
                    errorMessage.value = "Network Error"
                }
            }
        } catch (e: Exception) {
            // Handle other exceptions and set a generic error message
            errorMessage.value = "Network Error"
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
