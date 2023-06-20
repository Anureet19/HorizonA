package com.example.horizona

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.delay

//@Composable
//fun LoginScreen() {
//    val backgroundImage = painterResource(R.drawable.plantsoilapp) // Replace R.drawable.plantsoilapp with the actual resource ID of your image
//
//    BoxWithConstraints {
//        val constraints = constraints
//        val screenWidth = constraints.maxWidth
//        val screenHeight = constraints.maxHeight
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Image(
//                painter = backgroundImage,
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//
//            Column(
//                modifier = Modifier
//                    .padding(20.dp)
//                    .fillMaxWidth()
//                    .align(Alignment.Center)
//            ) {
//                Text(
//                    text = "Soil Analysis App",
//                    style = MaterialTheme.typography.h4,
//                    color = Color.White,
//                    modifier = Modifier
//                        .padding(bottom = 20.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//
//                Column(
//                    modifier = Modifier
//                        .background(color = Color.White)
//                        .padding(20.dp)
//                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(5.dp))
//                ) {
//                    // Login form
//                    Text(
//                        text = "Login",
//                        style = MaterialTheme.typography.h6,
//                        color = Color.Black,
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally)
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//                    // Add input fields here
//                    // Example: TextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = { })
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Button(
//                        onClick = { /* Handle login button click */ },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(text = "Login", color = Color.White)
//                    }
//                    Spacer(modifier = Modifier.height(10.dp))
//                    Text(
//                        text = "Don't have an account? Register here",
//                        color = Color.Gray,
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                }
//            }
//        }
//    }
//}

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

@Composable
fun SplashScreen(
    navigateToNextScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Simulate a delay of 2 seconds
        delay(2000)

        // Navigate to the next screen
        navigateToNextScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF52B669))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo_white),
                contentDescription = "Horizon-A Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
                    .size(400.dp) // Adjust the size as desired
            )
        }
    }
}

@Composable
fun WelcomeScreen() {
    val navigateToNextScreen = { /* Navigate to the next screen */ }

    Scaffold(
        content = {
            SplashScreen(navigateToNextScreen)
        }
    )
}
//@Composable
//fun ImageBackgroundScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF52B669))
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Image(
//                painter = painterResource(R.drawable.img),
//                contentDescription = "Background Image",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(
//                            Color(0xFF000000),
//                            Color.Transparent
//                        ),
//                        startY = 0f,
//                        endY = 1f
//                    )
//                )
//        ) {
//            Text(
//                text = "Welcome to AgriGrow",
//                fontFamily = FontFamily(Font(R.font.aoboshi_one)),
//                fontWeight = FontWeight.Normal,
//                fontSize = 36.sp,
//                lineHeight = 46.sp,
//                color = Color.White,
//                modifier = Modifier
//                    .padding(start = 47.dp, top = 110.dp)
//            )
//        }
//    }
//}

@Composable
fun ImageBackgroundScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backgroundImage, gradient, welcomeText, horizonAText, additionalText, signinGuest, signup) = createRefs()

        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "Background Image",
            modifier = Modifier
                .constrainAs(backgroundImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
//                .scale(scaleX = 1.2f, scaleY = 1.2f)
        )

        Canvas(
            modifier = Modifier
                .constrainAs(gradient) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize()
        ) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF8800).copy(alpha = 0.6f), // Transparent orange
                        Color(0xFFFFFFFF).copy(alpha = 0.2f),
                        Color(0xFF34A853).copy(alpha = 0.6f) // Semi-transparent green
                    ),
                    startY = 0f,
                    endY = size.height
                )
            )
        }

        Text(
            text = "Welcome to",
            fontFamily = FontFamily(Font(R.font.aoboshi_one)),
            fontSize = 36.sp,
            lineHeight = 46.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(parent.top, margin = 110.dp)
                start.linkTo(parent.start, margin = 47.dp)
            }
        )

        Text(
            text = "Horizon-A",
            fontFamily = FontFamily(Font(R.font.aoboshi_one)),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            lineHeight = 46.sp,
            color = Color(0xFF39FF14),
            modifier = Modifier.constrainAs(horizonAText) {
                top.linkTo(welcomeText.bottom)
                start.linkTo(parent.start, margin = 47.dp)
            }
        )
        Text(
            text = "Make Farming Easy by knowing your soil",
            fontFamily = FontFamily(Font(R.font.alatsi)),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 27.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(additionalText) {
                width = Dimension.value(266.dp)
                height = Dimension.value(54.dp)
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = 500.dp)
                end.linkTo(parent.end)
            }
        )
        val line = Modifier
            .size(width = 100.dp, height = 5.dp)
            .background(Color(255, 255, 255, 128))
            .rotate(180f)

        val lineRef = createRef()

        Box(
            modifier = Modifier
                .constrainAs(lineRef) {
                    width = Dimension.value(100.dp)
                    height = Dimension.value(3.dp)
                    start.linkTo(parent.start, margin = 160.dp)
                    top.linkTo(additionalText.bottom, margin = 20.dp)
                }
                .then(line)
        )

        val button1Clicked = remember { mutableStateOf(false) }
        val button2Clicked = remember { mutableStateOf(false) }

        val button1Text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.aoboshi_one)), fontWeight = FontWeight.Normal, fontSize = 16.sp, color = Color.White)) {
                append("Signin as Guest")
            }
        }

        val button2Text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.aoboshi_one)), fontWeight = FontWeight.Normal, fontSize = 16.sp, color = Color.White)) {
                append("Signup")
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(signinGuest) {
                    width = Dimension.value(304.dp)
                    height = Dimension.value(45.dp)
                    start.linkTo(parent.start, margin = 35.dp)
                    top.linkTo(lineRef.bottom, margin = 20.dp)
                    centerHorizontallyTo(parent)
                }
                .background(
                    color = Color(0x36FFFFFF), // Transparent white (0x36 = 54)
                    shape = RoundedCornerShape(30.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            ClickableText(
                text = button1Text,
                onClick = { button1Clicked.value = !button1Clicked.value },
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(signup) {
                    width = Dimension.value(304.dp)
                    height = Dimension.value(45.dp)
                    start.linkTo(parent.start, margin = 35.dp)
                    top.linkTo(signinGuest.bottom, margin = 10.dp)
                    centerHorizontallyTo(parent)
                }
                .background(
                    color = Color(0x36FFFFFF), // Transparent white (0x36 = 54)
                    shape = RoundedCornerShape(30.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            ClickableText(
                text = button2Text,
                onClick = { button2Clicked.value = !button2Clicked.value },
                modifier = Modifier.align(Alignment.Center)
            )
        }


    }
}












//@Composable
//fun WelcomeScreen() {
//    Surface(
//        color = Color(0xFFF2F2F2),
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(R.drawable.app_logo),
//                contentDescription = "App Logo",
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(bottom = 32.dp)
//            )
//            Button(
//                onClick = { /* Handle Registration */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .clip(RoundedCornerShape(28.dp))
//                    .background(Color(0xFF34BFA3))
//            ) {
//                Text(
//                    text = "REGISTER",
//                    color = Color.White,
//                    style = MaterialTheme.typography.button
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = { /* Handle Login */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .clip(RoundedCornerShape(28.dp))
//                    .border(1.dp, Color(0xFF34BFA3), RoundedCornerShape(28.dp))
//            ) {
//                Text(
//                    text = "LOGIN",
//                    color = Color(0xFF34BFA3),
//                    style = MaterialTheme.typography.button
//                )
//            }
//        }
//    }
//}


@Composable
fun SignUpScreen() {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val screenPadding = with(LocalDensity.current) { 16.dp }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(screenPadding)
    ) {
        LeftBox()
        RightBox()
        TextFields(modifier = Modifier.align(Alignment.Center))

        Text(
            text = "Sign Up",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 98.dp),
            fontFamily = averageSansFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.4127.sp,
            lineHeight = 44.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(248.dp)
                    .height(60.dp)
                    .offset(y=-(100).dp)
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
            ) {
                Text(
                    text = "Sign Up",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.08.em,
                    color = Color.White
                )
            }

            ClickableText(
                text = AnnotatedString("Already have an account? Login"),
                onClick = { offset ->
                    // Handle click event
                },
                modifier = Modifier
                    .padding(top = 16.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF5B5B5E)
                )
            )


        }

    }
}

@Composable
fun LeftBox() {
    Box(
        modifier = Modifier
            .size(165.dp)
            .offset((-5).dp, (-99).dp)
            .background(Color(0xFE724C4D), shape = CircleShape)
    )

    Box(
        modifier = Modifier
            .size(96.dp)
            .offset((-46).dp, (-21).dp)
            .border(width = 36.dp, color = Color(0xFF34A853), shape = CircleShape)
    )
}

@Composable
fun RightBox() {
    Box(
        modifier = Modifier
            .size(181.dp)
            .offset(298.dp, (-109).dp)
            .background(Color(0xFF34A853), shape = CircleShape)
    )
}


@Composable
fun TextFields(modifier: Modifier = Modifier) {
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val screenPadding = with(LocalDensity.current) { 16.dp }

    val name = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, top = screenPadding),
            textStyle = TextStyle(
                fontFamily = alatsiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = "Full Name",
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF9796A1)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color(0xFE724C4D),
                unfocusedBorderColor = Color(0xFE724C4D),
                focusedLabelColor = Color(0xFF9796A1),
                unfocusedLabelColor = Color(0xFF9796A1),
                backgroundColor = Color.White
            )
        )

        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, top = screenPadding),
            textStyle = TextStyle(
                fontFamily = alatsiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = "Address",
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF9796A1)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color(0xFF2372CF),
                unfocusedBorderColor = Color(0xFF2372CF),
                focusedLabelColor = Color(0xFF9796A1),
                unfocusedLabelColor = Color(0xFF9796A1),
                backgroundColor = Color.White
            )
        )

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, top = screenPadding),
            textStyle = TextStyle(
                fontFamily = alatsiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = "Phone Number",
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF9796A1)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color(0xFF34A853),
                unfocusedBorderColor = Color(0xFF34A853),
                focusedLabelColor = Color(0xFF9796A1),
                unfocusedLabelColor = Color(0xFF9796A1),
                backgroundColor = Color.White
            )
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, top = screenPadding),
            textStyle = TextStyle(
                fontFamily = alatsiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = "Email",
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF9796A1)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color(0xFE724C4D),
                unfocusedBorderColor = Color(0xFE724C4D),
                focusedLabelColor = Color(0xFF9796A1),
                unfocusedLabelColor = Color(0xFF9796A1),
                backgroundColor = Color.White
            )
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, top = screenPadding),
            textStyle = TextStyle(
                fontFamily = alatsiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = "Password",
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF9796A1)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color(0xFF2372CF),
                unfocusedBorderColor = Color(0xFF2372CF),
                focusedLabelColor = Color(0xFF9796A1),
                unfocusedLabelColor = Color(0xFF9796A1),
                backgroundColor = Color.White
            )
        )
    }
}


@Composable
fun LoginScreen() {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val screenPadding = with(LocalDensity.current) { 16.dp }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(screenPadding)
    ) {
        LeftBox()
        RightBox()

        Text(
            text = "Login",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 98.dp),
            fontFamily = averageSansFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.4127.sp,
            lineHeight = 44.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = screenPadding, top = screenPadding),
                textStyle = TextStyle(
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color.Black
                ),
                label = {
                    Text(
                        text = "Email",
                        fontFamily = alatsiFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF9796A1)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color(0xFE724C4D),
                    unfocusedBorderColor = Color(0xFE724C4D),
                    focusedLabelColor = Color(0xFF9796A1),
                    unfocusedLabelColor = Color(0xFF9796A1),
                    backgroundColor = Color.White
                )
            )

            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = screenPadding, top = screenPadding),
                textStyle = TextStyle(
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color.Black
                ),
                label = {
                    Text(
                        text = "Password",
                        fontFamily = alatsiFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF9796A1)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color(0xFF52B669),
                    unfocusedBorderColor = Color(0xFF52B669),
                    focusedLabelColor = Color(0xFF9796A1),
                    unfocusedLabelColor = Color(0xFF9796A1),
                    backgroundColor = Color.White
                )
            )

            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .width(248.dp)
                    .height(60.dp)
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
            ) {
                Text(
                    text = "Sign In",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.08.em,
                    color = Color.White
                )
            }

            ClickableText(
                text = AnnotatedString("Don't have an account? Sign up"),
                onClick = { offset ->
                    // Handle click event
                },
                modifier = Modifier
                    .padding(top = 16.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF5B5B5E)
                )
            )
        }
    }
}

// Rest of the code remains the same as provided in the previous response.


// Rest of the code remains the same as provided in the previous response.


//@Composable
//fun AgriGrowScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF52B669))
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp)
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(R.drawable.app_logo),
//                contentDescription = "Agri-Grow Logo",
//                modifier = Modifier.size(120.dp)
//            )
//            Spacer(modifier = Modifier.height(32.dp))
//            Text(
//                text = "Welcome to Agri-Grow",
//                style = MaterialTheme.typography.h4,
//                color = Color.White
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "We provide the best solutions for your agricultural needs.",
//                style = MaterialTheme.typography.body1,
//                color = Color.White,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(48.dp))
//            Button(
//                onClick = { /* Handle Get Started */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .clip(RoundedCornerShape(28.dp))
//                    .background(Color.White)
//            ) {
//                Text(
//                    text = "GET STARTED",
//                    color = Color(0xFF52B669),
//                    style = MaterialTheme.typography.button
//                )
//            }
//        }
//    }
//}


