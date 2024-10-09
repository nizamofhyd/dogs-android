package com.dogs.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dogs.R
import com.dogs.domain.models.Breed

@Composable
fun BreedsListScreen(
    modifier: Modifier = Modifier,
    breeds: List<Breed>,
    onBreedClick: (selectedBreedIndex: Int) -> Unit,
    onBreedSearch: (searchBreed: String) -> Unit
) {

    var searchText by rememberSaveable { mutableStateOf("") }


    Column(modifier = modifier) {

        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_10),
                horizontal = dimensionResource(id = R.dimen.padding_10)
            ),
            value = searchText, onValueChange = { findBreed ->
                searchText = findBreed
                onBreedSearch.invoke(findBreed)
            })

        LazyColumn(modifier = modifier) {

            items(breeds.size) { index ->
                val breed = breeds[index]

                Row(
                    modifier = Modifier
                        .clickable {
                            onBreedClick(index)
                        }
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.breed_image_height))
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_10),
                            horizontal = dimensionResource(id = R.dimen.padding_10)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_8))
                    ) {
                        Image(
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.breed_image_height))
                                .fillMaxHeight(),
                            painter = rememberAsyncImagePainter(
                                breed.image.url
                            ),
                            contentDescription = breed.name,
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Name
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = breed.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }


}