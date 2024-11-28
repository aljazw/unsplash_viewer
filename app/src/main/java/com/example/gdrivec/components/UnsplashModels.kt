package com.example.gdrivec.components

import androidx.compose.ui.graphics.painter.Painter

data class UnsplashResponse(
    val results: List<UnsplashImage>
)

data class UnsplashImage(
    val id: String,
    val description: String?,
    val urls: ImageUrls
)

data class ImageUrls(
    val small: String // This is the URL of the image to display
)

data class MockImage(val description: String?, val painter: Painter)
