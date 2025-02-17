package com.example.unsplashviewer.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashviewer.BuildConfig
import com.example.unsplashviewer.components.UnsplashImage
import com.example.unsplashviewer.network.RetrofitClient

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
    private val apiKey = BuildConfig.UNSPLASH_API_KEY
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
                    clientId = apiKey,
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
}
