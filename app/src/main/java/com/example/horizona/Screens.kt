package com.example.horizona

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.amplifyframework.core.Amplify
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.features.get
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SessionScreen(
    viewModel: AuthViewModel,
    imageState: MutableState<ImageState>,
    getImageLauncher: ActivityResultLauncher<String>,
    uploadPhoto: (Uri) -> Unit,
    downloadPhoto: () -> Unit
) {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))

    LaunchedEffect(Unit) {
        imageState.value = ImageState.Initial
    }

    LeftBox()
    RightBox()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        when (val state = imageState.value) {
            // Show Open Gallery Button
            is ImageState.Initial -> {
                Button(
                    onClick = { getImageLauncher.launch("image/*") },
                    modifier = Modifier
                        .width(248.dp)
                        .height(60.dp)
                        .padding(top = 24.dp),
                    shape = RoundedCornerShape(60f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
                ) {
                    Text(
                        text = "Open Gallery",
                        fontFamily = averageSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.08.em,
                        color = Color.White
                    )
                }
            }

            // Show Selected Photo and Upload Button
            is ImageState.ImageSelected -> {
                Image(
                    painter = rememberImagePainter(state.imageUri),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Button(
                    onClick = { uploadPhoto(state.imageUri) },
                    modifier = Modifier
                        .width(248.dp)
                        .height(60.dp)
                        .padding(top = 24.dp),
                    shape = RoundedCornerShape(60f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
                ) {
                    Text(
                        text = "Upload Photo",
                        fontFamily = averageSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.08.em,
                        color = Color.White
                    )
                }
            }

            // Show Image Upload Success Message
            is ImageState.ImageUploaded -> {
                Text(
                    text = "Image Upload successful!",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.08.em,
                    color = Color.Black
                )
                Button(
                    onClick = viewModel::showDashboard,
                    shape = RoundedCornerShape(60f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
                ) {
                    Text(
                        text = "Go to Dashboard",
                        fontFamily = averageSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.08.em,
                        color = Color.White
                    )
                }
            }

            // Show downloaded image
//            is ImageState.ImageDownloaded -> {
//                Image(
//                    painter = rememberImagePainter(state.downloadedImageFile),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }

            else -> {}
        }
    }
}

@Composable
fun VerificationCodeScreen(viewModel: AuthViewModel) {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val state by viewModel.verificationCodeState

    val screenPadding = with(LocalDensity.current) { 16.dp }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(screenPadding)
    ) {
        LeftBox()
        RightBox()
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = state.code,
                onValueChange = { viewModel.updateVerificationCodeState(code = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = screenPadding),
                textStyle = TextStyle(
                    fontFamily = alatsiFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color.Black
                ),
                label = {
                    Text(
                        text = "Verification Code",
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

            Button(
                onClick = viewModel::verifyCode,
                modifier = Modifier
                    .width(248.dp)
                    .height(60.dp)
                    .padding(top = 24.dp),
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF52B669))
            ) {
                Text(
                    text = "Verify",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.08.em,
                    color = Color.White
                )
            }
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

@Composable
fun ImageBackgroundScreen(viewModel: AuthViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backgroundImage, gradient, welcomeText, horizonAText, additionalText, signinGuest, signup, loginText) = createRefs()

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
                onClick = { viewModel.login() },
//                    button1Clicked.value = !button1Clicked.value },
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
                onClick = { viewModel.showSignUp() },
//                    button2Clicked.value = !button2Clicked.value },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        ClickableText(
            text = AnnotatedString("Already have an account? Login"),
            onClick = { viewModel.showLogin()
                // Handle click event
            },
            modifier = Modifier
                .constrainAs(loginText) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(signup.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(top = 16.dp)
                .wrapContentSize(),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Bold)),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )


    }
}

@Composable
fun SignUpScreen(viewModel: AuthViewModel) {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    //states
    val state by viewModel.signUpState

    val screenPadding = with(LocalDensity.current) { 16.dp }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(screenPadding)
    ) {
        LeftBox()
        RightBox()
        TextFields(modifier = Modifier.align(Alignment.Center), state, viewModel)

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
                onClick = viewModel::signUp,
                modifier = Modifier
                    .width(248.dp)
                    .height(60.dp)
                    .offset(y = -(50).dp)
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
                    viewModel.showLogin()
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
fun TextFields(modifier: Modifier = Modifier, state: SignUpState, viewModel: AuthViewModel) {
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val screenPadding = with(LocalDensity.current) { 16.dp }

    // Clear the values in the state object when recomposed
    DisposableEffect(Unit) {
        onDispose {
            viewModel.updateSignUpState(
                fullName = "",
                address = "",
                phoneNumber = "",
                username = "",
                email = "",
                password = ""
            )
        }
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = state.fullName,
            onValueChange = { viewModel.updateSignUpState(fullName = it) },
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
            value = state.address,
            onValueChange = { viewModel.updateSignUpState(address = it) },
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
            value = state.phoneNumber,
            onValueChange = {  viewModel.updateSignUpState(phoneNumber = it) },
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
            value = state.username,
            onValueChange = { viewModel.updateSignUpState(username = it) },
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
                    text = "Username",
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
            value = state.email,
            onValueChange = { viewModel.updateSignUpState(email = it) },
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
            value = state.password,
            onValueChange = { viewModel.updateSignUpState(password = it) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
fun LoginScreen(viewModel: AuthViewModel) {
    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    //state management
    val state by viewModel.loginState

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
                value = state.email,
                onValueChange = { viewModel.updateLoginState(email = it) },
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
                value = state.password,
                onValueChange = { viewModel.updateLoginState(password = it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                onClick = viewModel::login,
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
                onClick = { offset -> viewModel.showSignUp()
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

//@Composable
//fun DashboardScreen() {
//    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
//    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))
//
//    // Dummy data for parameters
//    val soilTypeOptions = listOf("Gravel %", "Sand %", "Soil %")
//    val soilColor = remember { mutableStateOf("Brown") }
//    val phLevelRange = remember { mutableStateOf("5.5 - 6.5") }
//    val organicMatterLevel = remember { mutableStateOf("High") }
//
//    LeftBox()
//    RightBox()
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Image Holder
//        Image(
//            painter = painterResource(id = R.drawable.img),
//            contentDescription = null,
//            modifier = Modifier
//                .size(200.dp)
//                .padding(top = 24.dp)
//        )
//
//        // Soil Parameters Sublist
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, top = 16.dp)
//        ) {
//            // Soil Type Sublist
//            Text(
//                text = "Soil Type:",
//                fontFamily = averageSansFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 18.sp,
//                lineHeight = 23.sp,
//                textAlign = TextAlign.Center,
//                letterSpacing = 0.08.em,
//                color = Color.Black
//            )
//
//            soilTypeOptions.forEach { option ->
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(16.dp)
//                            .background(Color.Gray)
//                            .clip(CircleShape)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = option,
//                        fontFamily = averageSansFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 16.sp,
//                        lineHeight = 20.sp,
//                        color = Color.Black
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            // Soil Color
//            Text(
//                text = "Soil Color: ${soilColor.value}",
//                fontFamily = averageSansFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 18.sp,
//                lineHeight = 23.sp,
//                textAlign = TextAlign.Center,
//                letterSpacing = 0.08.em,
//                color = Color.Black
//            )
//
//            // PH Level Range
//            Text(
//                text = "PH Level range: ${phLevelRange.value}",
//                fontFamily = averageSansFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 18.sp,
//                lineHeight = 23.sp,
//                textAlign = TextAlign.Center,
//                letterSpacing = 0.08.em,
//                color = Color.Black
//            )
//
//            // Organic Matter Level
//            Text(
//                text = "Organic Matter Level: ${organicMatterLevel.value}",
//                fontFamily = averageSansFontFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 18.sp,
//                lineHeight = 23.sp,
//                textAlign = TextAlign.Center,
//                letterSpacing = 0.08.em,
//                color = Color.Black
//            )
//        }
//    }
//}
//@Composable
//fun DashboardScreen() {
//    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
//    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))
//
//    // Dummy data for parameters
//    val gravelPercentage = 30f
//    val sandPercentage = 40f
//    val soilPercentage = 30f
//    val soilColor = remember { mutableStateOf("Brown") }
//    val phLevelRange = remember { mutableStateOf("5.5 - 6.5") }
//    val organicMatterLevel = remember { mutableStateOf("High") }
//
//    val screenPadding = with(LocalDensity.current) { 16.dp }
//
//    // Initialize the AuthViewModel
//    val authViewModel: AuthViewModel = viewModel()
//
//    // Observe the username state
//    val usernameState by rememberUpdatedState(newValue = authViewModel.signUpState.value.username)
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(screenPadding)
//    ) {
//        LeftBox()
//        RightBox()
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 100.dp)
//                .background(Color.White),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Image Holder
//            Image(
//                painter = painterResource(id = R.drawable.img),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(200.dp)
//                    .padding(top = 24.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//            // Soil Type Pie Chart
//            PieChart(
//                gravelPercentage = gravelPercentage,
//                sandPercentage = sandPercentage,
//                soilPercentage = soilPercentage
//            )
//        }
//
//        // Soil Parameters Box
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.LightGray)
//                .padding(screenPadding)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                // Soil Color
//                Text(
//                    text = "Soil Color: ${soilColor.value}",
//                    fontFamily = averageSansFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 18.sp,
//                    color = Color.Black
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // PH Level Range
//                Text(
//                    text = "PH Level range: ${phLevelRange.value}",
//                    fontFamily = averageSansFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 18.sp,
//                    color = Color.Black
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Organic Matter Level
//                Text(
//                    text = "Organic Matter Level: ${organicMatterLevel.value}",
//                    fontFamily = averageSansFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 18.sp,
//                    color = Color.Black
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun PieChart(gravelPercentage: Float, sandPercentage: Float, soilPercentage: Float) {
//    val totalPercentage = gravelPercentage + sandPercentage + soilPercentage
//    val canvasSize = 200.dp
//    val centerOffset = with(LocalDensity.current) { Offset(canvasSize.toPx() / 2, canvasSize.toPx() / 2) }
//    val radius = with(LocalDensity.current) { canvasSize.toPx() / 2 }
//    val sweepAngles = listOf(
//        gravelPercentage / totalPercentage * 360f,
//        sandPercentage / totalPercentage * 360f,
//        soilPercentage / totalPercentage * 360f
//    )
//
//    Canvas(
//        modifier = Modifier.size(canvasSize)
//    ) {
//        var startAngle = 0f
//        sweepAngles.forEachIndexed { index, sweepAngle ->
//            drawArc(
//                color = when (index) {
//                    0 -> Color(0xFFE91E63)
//                    1 -> Color(0xFF03A9F4)
//                    else -> Color(0xFF4CAF50)
//                },
//                startAngle = startAngle,
//                sweepAngle = sweepAngle,
//                useCenter = true,
//                topLeft = centerOffset - Offset(radius, radius),
//                size = Size(radius * 2, radius * 2),
//            )
//            startAngle += sweepAngle
//        }
//    }
//}


@Composable
fun DashboardScreen() {
    // State to hold the soil data fetched from the API
    var soilDataState = remember { mutableStateOf<SoilData?>(null) }

    // Fetch the data from the API. You need to replace this with the actual API call.
//    fetchSoilData { soilData ->
//        soilDataState.value = soilData
//    }
    // Coroutine to fetch the data from the API
    var errorMessage = remember { mutableStateOf<String?>(null) }

    // Fetch the data from the API using coroutine
    LaunchedEffect(true) {
        fetchSoilData(
            onSuccess = { soilData ->
                soilDataState.value = soilData
            },
            onError = { error ->
                errorMessage.value = error
                Log.d("dhruv error message", errorMessage.toString())
            }
        )
    }

    val averageSansFontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal))
    val alatsiFontFamily = FontFamily(Font(R.font.alatsi))

    val screenPadding = with(LocalDensity.current) { 16.dp }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(screenPadding)
    ) {
        if (errorMessage.value != null) {
            Log.d("dhruv", "nono")
            Log.d("dhruv error message", errorMessage.toString())
            // Show error message if there's an error
            Text(
                text = errorMessage.value ?: "", // Use the value property to access the String value from MutableState
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        } else {
            Log.d("dhruv", "welcome")
            soilDataState.value?.let { soilData ->
                LeftBox()
                RightBox()
                ScrollableContent(
                    soilData = soilData,
                    averageSansFontFamily = averageSansFontFamily
                )
            }
        }
    }
}

@Composable
fun ScrollableContent(soilData: SoilData, averageSansFontFamily: FontFamily) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Image Holder (Assuming you have an image for the soil type)
            // Replace "R.drawable.img" with the actual resource ID of the image
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Image Holder (Assuming you have an image for the soil type)
                // Replace "R.drawable.img" with the actual resource ID of the image
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 24.dp)
                )

                // Soil Type Pie Chart
                soilData.Composition?.let { composition ->
                    PieChart(
                        gravelPercentage = composition.Gravel.toFloat(),
                        sandPercentage = composition.Sand.toFloat(),
                        soilPercentage = composition.Silt.toFloat()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            soilData.Color?.let { color ->
                // Soil Color
                Text(
                    text = "Soil Color: $color",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            soilData.pHRange?.let { pHRange ->
                // PH Level Range
                Text(
                    text = "PH Level range: $pHRange",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            soilData.AssociatedNutrients?.let { nutrients ->
                // Associated Nutrients
                Text(
                    text = "Associated Nutrients: $nutrients",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            soilData.FertilizerRecommendation?.let { fertilizer ->
                // Fertilizer Recommendation
                Text(
                    text = "Fertilizer Recommendation: $fertilizer",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            soilData.CropPlantRecommendation?.let { cropPlant ->
                // Crop/Plant Recommendation
                Text(
                    text = "Crop/Plant Recommendation: $cropPlant",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            soilData.GeneralRecommendation?.let { generalRecommendation ->
                // General Recommendation
                Text(
                    text = "General Recommendation: $generalRecommendation",
                    fontFamily = averageSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PieChart(gravelPercentage: Float, sandPercentage: Float, soilPercentage: Float) {
    val totalPercentage = gravelPercentage + sandPercentage + soilPercentage
    val canvasSize = 150.dp
    val centerOffset = with(LocalDensity.current) { Offset(canvasSize.toPx() / 2, canvasSize.toPx() / 2) }
    val radius = with(LocalDensity.current) { canvasSize.toPx() / 2 }
    val strokeWidth = with(LocalDensity.current) { 30.dp.toPx() } // Convert Dp to pixels

    Canvas(
        modifier = Modifier.size(canvasSize)
    ) {
        var startAngle = 0f
        drawArc(
            color = Color.Transparent, // Use Transparent color for the center part
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = centerOffset - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = Color(0xFFE91E63),
            startAngle = startAngle,
            sweepAngle = gravelPercentage / totalPercentage * 360f,
            useCenter = false,
            topLeft = centerOffset - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )
        startAngle += gravelPercentage / totalPercentage * 360f

        drawArc(
            color = Color(0xFF03A9F4),
            startAngle = startAngle,
            sweepAngle = sandPercentage / totalPercentage * 360f,
            useCenter = false,
            topLeft = centerOffset - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )
        startAngle += sandPercentage / totalPercentage * 360f

        drawArc(
            color = Color(0xFF4CAF50),
            startAngle = startAngle,
            sweepAngle = soilPercentage / totalPercentage * 360f,
            useCenter = false,
            topLeft = centerOffset - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )
    }
}

//suspend fun fetchSoilData(onSuccess: (SoilData) -> Unit) {
//    Log.e("dhruv", "In the function")
//    val client = HttpClient {
//        install(JsonFeature) {
//            serializer = KotlinxSerializer()
//        }
//    }
//
//    try {
//        // Make the API call using ktor-client within a coroutine
//        val soilData: SoilData = withContext(Dispatchers.IO) {
//            client.get("http://192.168.0.147:5000/predict")
//        }
//        Log.e("dhruv", "Successfully made API call")
//
//        // Call the callback function to update the state with the fetched data
//        onSuccess(soilData)
//    } catch (e: Exception) {
//        // Handle any exceptions or errors that may occur during the API call
//        e.printStackTrace()
//    } finally {
//        client.close()
//    }
//}

suspend fun fetchSoilData(onSuccess: (SoilData) -> Unit, onError: (String) -> Unit) {
    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    try {
        // Make the API call using ktor-client
        val soilData: SoilData = client.get("http://192.168.0.147:5000/predict")

        // Call the callback function to update the state with the fetched data
        onSuccess(soilData)
    } catch (e: Exception) {
        // Handle any exceptions or errors that may occur during the API call
        Log.e("Api fuck hogyi", e.toString())
        onError("Failed to fetch soil data. Please try again later.")
        e.printStackTrace()
    } finally {
        client.close()
    }
}




// Replace this function with the actual API call to "/predict"
//fun fetchSoilData(onSuccess: (SoilData) -> Unit) {
//    // Simulating the data received from the API
//    val jsonString = """
//        {
//            "Category": "soil",
//            "Type": "Clay",
//            "Composition": {
//                "Gravel": 10.0,
//                "Sand": 80.0,
//                "Silt": 10.0
//            },
//            "Color": "Dark Brown",
//            "pHRange": "5.5 - 7.0",
//            "AssociatedNutrients": "Organic matter, Carbon, Nitrogen, Phosphorus, Potassium",
//            "FertilizerRecommendation": "Use balanced NPK fertilizers to provide essential nutrients to plants. Consider using slow-release fertilizers to reduce nutrient leaching in sandy soils.",
//            "CropPlantRecommendation": "Beans, Peas, Sweet Potatoes, Carrots, Drought-resistant plants like Succulents and Cacti, Deep-rooted plants that can access water deeper in the soil.",
//            "GeneralRecommendation": "Improve soil structure with organic matter to enhance water retention. Mulch the soil to reduce evaporation and maintain moisture."
//        }
//    """.trimIndent()
//
//    // Deserialize the JSON data into a SoilData object
//    val soilData = Gson().fromJson(jsonString, SoilData::class.java)
//
//    // Call the callback function to update the state with the fetched data
//    onSuccess(soilData)
//}




