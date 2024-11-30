package com.example.unsplashviewer.components

data class FirestoreImage(
    val id: String,
    val description: String?,
    val urls: ImageUrls,
    val createdAt: Long
)