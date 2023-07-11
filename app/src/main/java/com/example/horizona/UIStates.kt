package com.example.horizona

import android.location.Address

data class SignUpState(
    var fullName: String="",
    var address: String="",
    var phoneNumber: Number=9999999999,
    var email: String ="",
    var password: String =""
)

data class LoginState(
    var email: String ="",
    var password: String =""
)

data class VerificationCodeState(
    var email: String ="",
    var code: String =""
)