package com.example.horizona

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.horizona.ui.theme.HorizonATheme
import java.io.File

class MainActivity : ComponentActivity() {

    companion object {
        const val PHOTO_KEY = "my-photo.jpg"
    }

    private var imageState = mutableStateOf<ImageState>(ImageState.Initial)

    private val getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageState.value = ImageState.ImageSelected(it) }
    }
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAmplify()

        viewModel.configureAmplify(this)
        setContent {
            HorizonATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    DashboardScreen()
                    AppNavigator()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun AppNavigator() {
        val navController = rememberNavController()
        viewModel.navigateTo = {
            navController.navigate(it)
        }

        // Observe the current destination and decide whether to show the navigation bar or not
        val currentDestinationRoute = navController.currentDestination?.route
        val showNavigationBar = currentDestinationRoute != "welcomeScreen"

        Scaffold(
            topBar = {
                if (showNavigationBar) {
                    NavigationBar(
                        title = "",
                        onBackClicked = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        ) {
            NavHost(navController = navController, startDestination = "welcomeScreen") {
                composable("welcomeScreen") {
                    ImageBackgroundScreen(viewModel = viewModel)
                }
                composable("login") {
                    LoginScreen(viewModel = viewModel)
                }
                composable("signUp") {
                    SignUpScreen(viewModel = viewModel)
                }
                composable("verify") {
                    VerificationCodeScreen(viewModel = viewModel)
                }
                composable("session") {
                    SessionScreen(
                        viewModel = viewModel,
                        imageState,
                        getImageLauncher,
                        ::uploadPhoto,
                        ::downloadPhoto
                    )
                }
                composable("dashboard") {
                    DashboardScreen()
                }
            }
        }
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("kilo", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("kilo", "Could not initialize Amplify", error)
        }
    }

    private fun uploadPhoto(imageUri: Uri) {
        val stream = contentResolver.openInputStream(imageUri)!!

        Amplify.Storage.uploadInputStream(
            "my-photo.jpg",
            stream,
            { imageState.value = ImageState.ImageUploaded },
            { error -> Log.e("kilo", "Failed upload", error) }
        )
    }

    private fun downloadPhoto() {
        val localFile = File("${applicationContext.filesDir}/downloaded-image.jpg")

        Amplify.Storage.downloadFile(
            PHOTO_KEY,
            localFile,
            { imageState.value = ImageState.ImageDownloaded(localFile) },
            { Log.e("kilo", "Failed download", it) }
        )
    }
    @Composable
    fun NavigationBar(
        title: String,
        onBackClicked: () -> Unit
    ) {
        TopAppBar(
            backgroundColor = Color(0xFF52B669),
            elevation = 4.dp,
            modifier = Modifier.height(45.dp)
        ) {
            IconButton(
                onClick = { onBackClicked() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = title,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontFamily = FontFamily(Font(R.font.average_sans, FontWeight.Normal)),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }


}
