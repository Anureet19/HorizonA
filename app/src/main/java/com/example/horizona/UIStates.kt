package com.example.horizona

import android.location.Address

data class SignUpState(
    var fullName: String="",
    var address: String="",
    var phoneNumber: String="",
    var username: String="",
    var email: String ="",
    var password: String =""
)

data class LoginState(
    var email: String ="",
    var password: String =""
)

data class VerificationCodeState(
    var username: String ="",
    var code: String =""
)