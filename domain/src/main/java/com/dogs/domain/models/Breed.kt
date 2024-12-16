package com.dogs.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Breed(
    val name: String,
    val bredFor: String?,
    val lifeSpan: String,
    val temperament: String?,
    val image: @RawValue Image
) : Parcelable