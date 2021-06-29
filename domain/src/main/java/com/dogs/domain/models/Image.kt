package com.dogs.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image constructor(val url: String, val width: Int, val height: Int): Parcelable
