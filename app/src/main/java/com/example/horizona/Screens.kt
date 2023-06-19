package com.example.horizona

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
@Composable
fun SessionScreen(viewModel: AuthViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "YOU HAVE LOGGED IN")
        Button(onClick = viewModel::logOut) {
            Text("Log Out")
        }
    }
}

@Composable
fun SignUpScreen(viewModel: AuthViewModel) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Name") }
        )

        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Address") }
        )

        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Phone Number") }
        )

        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Email") }
        )

        TextField(
            value = "",
            onValueChange = { },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(text = "Password") }
        )

        Button(onClick = viewModel::signUp) {
            Text(text = "Sign Up")
        }

        TextButton(onClick = viewModel::showLogin) {
            Text(text = "Already have an account? Login.")
        }
    }
}

@Composable
fun LoginScreen(viewModel: AuthViewModel) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Username") }
        )

        TextField(
            value = "",
            onValueChange = { },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(text = "Password") }
        )

        Button(onClick = viewModel::login) {
            Text(text = "Login")
        }

        TextButton(onClick = viewModel::showSignUp) {
            Text(text = "Don't have an account? Sign up.")
        }
    }
}

@Composable
fun VerificationCodeScreen(viewModel: AuthViewModel) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = "",
            onValueChange = { },
            placeholder = { Text(text = "Verification Code") }
        )

        Button(onClick = viewModel::verifyCode) {
            Text(text = "Verify")
        }
    }
}

