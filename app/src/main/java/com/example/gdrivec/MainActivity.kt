package com.example.gdrivec
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gdrivec.screens.MainScreen
import com.example.gdrivec.ui.theme.GDriveCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GDriveCTheme {
                MainScreen()
            }
        }
    }
}



