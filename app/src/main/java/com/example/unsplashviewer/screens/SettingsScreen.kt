package com.example.unsplashviewer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.unsplashviewer.R
import com.example.unsplashviewer.ui.theme.CustomTheme
import com.example.unsplashviewer.viewmodel.AuthState
import com.example.unsplashviewer.viewmodel.AuthViewModel


@Composable
fun SettingsScreen(navController: NavController, authViewModel: AuthViewModel, modifier: Modifier = Modifier){

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Added padding around the screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Missing features? Just consider it 'minimalistic'!",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp,bottom = 42.dp)
                .fillMaxWidth(),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )
        )

        val image: Painter = painterResource(id = R.drawable.settingspic)
        Image(
            painter = image,
            contentDescription = "Sample Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        // Sign out button with padding
        TextButton(
            onClick = { authViewModel.signout() },
            modifier = Modifier.padding(bottom = 40.dp),
        ) {
            Text(
                text = "Sign out",
                style = TextStyle(
                    color = CustomTheme.colors.error,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.4f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenVisualPreview() {
    // Mock Modifier
    val previewModifier = Modifier

    Column(
        modifier = previewModifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Missing features? Just consider it 'minimalistic'!",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 42.dp)
                .fillMaxWidth(),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )
        )

        val image: Painter = painterResource(id = R.drawable.settingspic)
        Image(
            painter = image,
            contentDescription = "Sample Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        TextButton(
            onClick = { /* No action in preview */ },
            modifier = Modifier.padding(bottom = 40.dp),
        ) {
            Text(
                text = "Sign out",
                style = TextStyle(
                    color = CustomTheme.colors.error,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.4f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                ),
            )
        }
    }
}





