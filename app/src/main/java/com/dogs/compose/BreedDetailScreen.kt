package com.dogs.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun BreedDetailScreen(modifier: Modifier = Modifier, breed: Breed) {

    LazyColumn(modifier = modifier) {
        item {
            Column(
                modifier = modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_10),
                    vertical = dimensionResource(
                        id = R.dimen.padding_10
                    )
                )
            ) {

                Image(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.breed_image_height_detail))
                        .fillMaxWidth(),
                    painter = rememberAsyncImagePainter(
                        breed.image.url
                    ),
                    contentDescription = breed.name,
                    contentScale = ContentScale.Crop
                )

                // Name
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = dimensionResource(id = R.dimen.padding_10)),
                    text = breed.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    textAlign = TextAlign.Left
                )

                // Lifespan
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = dimensionResource(id = R.dimen.padding_10)),
                    text = breed.lifeSpan,
                    fontSize = 16.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Left
                )

                // Bredfor
                breed.bredFor?.let { bredFor ->
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = dimensionResource(id = R.dimen.padding_10)),
                        text = bredFor,
                        fontSize = 16.sp,
                        maxLines = 1,
                        textAlign = TextAlign.Left
                    )
                }

                // Temperament
                breed.temperament?.let { temperament ->
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = dimensionResource(id = R.dimen.padding_10)),
                        text = temperament,
                        fontSize = 16.sp,
                        maxLines = 1,
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }

}