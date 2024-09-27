package com.dogs.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dogs.domain.models.Breed

@Composable
fun BreedDetailScreen(modifier: Modifier = Modifier, breed: Breed) {

    Column(modifier = modifier) {

        Image(
            modifier = Modifier
                //.width(dimensionResource(R.dimen.carousel_card_width))
                .height(200.dp)
                .fillMaxWidth(),
            painter = rememberAsyncImagePainter(
                breed.image.url
            ),
            contentDescription = breed.name,
            contentScale = ContentScale.Crop
        )

        // Name
        Text(
            modifier = Modifier.wrapContentSize(),
            text = breed.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Left
        )

        // Lifespan
        Text(
            modifier = Modifier.wrapContentSize(),
            text = breed.lifeSpan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Left
        )

        // Bredfor
        breed.bredFor?.let { bredFor ->
            Text(
                modifier = Modifier.wrapContentSize(),
                text = bredFor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = TextAlign.Left
            )
        }

        // Temperament
        breed.temperament?.let { temperament ->
            Text(
                modifier = Modifier.wrapContentSize(),
                text = temperament,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = TextAlign.Left
            )
        }
    }
}