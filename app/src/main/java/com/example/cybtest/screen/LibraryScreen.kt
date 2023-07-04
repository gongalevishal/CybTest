package com.example.cybtest.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cybtest.connectivity.ConnectivityObservable
import com.example.cybtest.modal.ApiResponse
import com.example.cybtest.util.AttributionText
import com.example.cybtest.util.CharacterImage
import com.example.cybtest.util.NetworkResult
import com.example.cybtest.viewmodel.ComicApiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(paddingValues: PaddingValues) {
    val comicApiViewModel: ComicApiViewModel = viewModel()
    val result by comicApiViewModel.result.collectAsState()
    val text = comicApiViewModel.queryText.collectAsState()
    val networkAvailable =
        comicApiViewModel.networkAvailable.observe().collectAsState(ConnectivityObservable.Status.Available)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (networkAvailable.value == ConnectivityObservable.Status.Unavailable) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Network unavailable",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
        OutlinedTextField(
            value = text.value,
            onValueChange = comicApiViewModel::onQueryUpdate,
            label = { Text(text = "Search movie") },
            placeholder = { Text(text = "Movie name please...") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search movie Name")
                }

                is NetworkResult.Success -> {
                    ShowCharactersList(result)
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }
        }
    }

}

@Composable
fun ShowCharactersList(
    result: NetworkResult<ApiResponse>,
) {
    result.data?.results?.let { characters ->

        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Top
        ) {
            result.data.results?.first()?.title.let {
                item {
                    AttributionText(text = it!!)
                }
            }

            items(characters) { character ->
                val imageUrl = character.posterPath
                val title = character.title
                val description = character.overview

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()

                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CharacterImage(
                            uri = imageUrl,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)
                        )

                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)

                            Text(modifier = Modifier.padding(top = 2.dp), text = description, maxLines = 5, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}