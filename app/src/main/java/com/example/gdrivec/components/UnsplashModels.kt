package com.example.gdrivec.components

data class UnsplashResponse(
    val results: List<UnsplashImage>
)

data class UnsplashImage(
    val id: String,
    val urls: ImageUrls
)

data class ImageUrls(
    val small: String // This is the URL of the image to display
)