package com.example.unsplashviewer.components


import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class FirestoreRepository {

    // Function to upload image data to Firestore
    fun uploadImageToFirestore(image: UnsplashImage, context: android.content.Context) {

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()

            // Create a map for the image data
            val imageDetails = hashMapOf(
                "id" to image.id,
                "url" to image.urls.small,
                "description" to image.description,
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("users")
                .document(userId)
                .collection("images")
                .add(imageDetails)
                .addOnSuccessListener {
                    Toast.makeText(context, "Image data uploaded!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error uploading data", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to fetch images for the logged-in user
    fun fetchUserImages(
        onSuccess: (List<FirestoreImage>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val images = mutableListOf<FirestoreImage>()

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()

            // Fetch images from Firestore for the current user
            db.collection("users")
                .document(userId)
                .collection("images")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        for (document in snapshot.documents) {
                            val image = FirestoreImage(
                                id = document.getString("id") ?: "",
                                urls = ImageUrls(small = document.getString("url") ?: ""),
                                description = document.getString("description") ?: "",
                                createdAt = document.getLong("createdAt") ?: 0L
                            )
                            images.add(image)
                        }
                        onSuccess(images)
                    } else {
                        onSuccess(emptyList())
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure("Error fetching data: ${exception.message}")
                }
        } else {
            onFailure("User not logged in")
        }
    }

    fun removeImageFromFirestore(imageId: String, context: android.content.Context, onSuccess: () -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val db = FirebaseFirestore.getInstance()

            db.collection("users")
                .document(userId)
                .collection("images")
                .whereEqualTo("id", imageId)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        for (document in snapshot.documents) {
                            // Delete the document
                            db.collection("users")
                                .document(userId)
                                .collection("images")
                                .document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    onSuccess()
                                    Toast.makeText(context, "Image removed successfully!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        context,
                                        "Error removing image: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(context, "Image not found!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        context,
                        "Error finding image: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

}





