package com.example.gdrivec.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gdrivec.viewmodel.UnsplashViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: UnsplashViewModel = viewModel(), modifier: Modifier = Modifier) {

    val images by viewModel.images.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                if(text.isNotEmpty()) {
                    viewModel.searchImages(text)
                    active = false
                }
           },
            active = active,
            onActiveChange =  {active = it },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if(active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if(text.isNotEmpty()) {
                                text = ""
                            }else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            },
            modifier = Modifier.padding(bottom = 15.dp),
        ) {}
        if(isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        LazyColumn {
            itemsIndexed(images) { index, image ->
                if (index == images.lastIndex) {
                    viewModel.loadNextPage()
                }
                AsyncImage(
                    model = image.urls.small,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp)
                )
            }

            // Show loading indicator
            if (isLoading) {
                item {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))
                }
            }

            // Display error message
            errorMessage?.let { message ->
                item {
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}