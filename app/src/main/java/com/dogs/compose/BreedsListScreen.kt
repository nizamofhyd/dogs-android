package com.dogs.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dogs.domain.models.Breed

@Composable
fun BreedsListScreen(
    modifier: Modifier = Modifier,
    breeds: List<Breed>,
    onBreedClick: (selectedBreedIndex: Int) -> Unit
) {

    LazyColumn(modifier = modifier) {

        items(breeds.size) { index ->
            val breed = breeds[index]

            Row(
                modifier = Modifier
                    .clickable {
                        onBreedClick(index)
                    }
                    .fillMaxWidth()
                    //.height(dimensionResource(id = R.dimen.carousel_height))
                    .height(150.dp)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxHeight(),
                    //shape = RoundedCornerShape(dimensionResource(R.dimen.carousel_image_corner_radius))
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            //.width(dimensionResource(R.dimen.carousel_card_width))
                            .width(150.dp)
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