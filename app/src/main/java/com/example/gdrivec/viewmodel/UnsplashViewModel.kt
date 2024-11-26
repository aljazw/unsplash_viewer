package com.example.gdrivec.viewmodel


import androidx.compose.runtime.currentCompositionErrors
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gdrivec.components.UnsplashImage
import com.example.gdrivec.network.RetrofitClient

import kotlinx.coroutines.launch

class UnsplashViewModel : ViewModel() {
    // LiveData for holding the list of Unsplash images
    private val _images = MutableLiveData<List<UnsplashImage>>()
    val images: LiveData<List<UnsplashImage>> = _images

    // LiveData for showing loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Pagination variables
    private var currentPage = 1
    private var isLastPage = false // To avoid fetching if we've reached the last page
    private var query: String = ""

    // Function to search for images based on a query


    fun searchImages(newQuery: String) {
        query = newQuery
        currentPage = 1
        isLastPage = false
        _images.value = emptyList()
        fetchImages()
    }


    fun loadNextPage() {
        if(_isLoading.value == true || isLastPage) return
        currentPage++
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.unsplashApi.searchPhotos(
                    query = query,
                    clientId = "S8MTdNEY09YPKgHQJo_LVwZ6uwRu9lapm3JlE8aR2NM",
                    page = currentPage,
                    perPage = 10
                )

                if (response.results.isEmpty()) {
                    isLastPage = true
                } else {
                    val updatedImages = _images.value.orEmpty() + response.results
                    _images.value = updatedImages
                }

                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Failed to load: ${e.message}"
                currentPage--
            } finally {
                _isLoading.value = false
            }
        }
    }


//    fun searchImages(query: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                // Making the network request via Retrofit
//                val response = RetrofitClient.unsplashApi.searchPhotos(
//                    query = query,
//                    clientId = , // Replace with your actual API key
//                    page = 1,
//                    perPage = 20,
//                )
//                // Updating LiveData with the results
//                _images.value = response.results
//            } catch (e: Exception) {
//                e.printStackTrace()  // Handle the error
//            } finally {
//                // Hide the loading indicator
//                _isLoading.value = false
//            }
//        }
//    }
}
