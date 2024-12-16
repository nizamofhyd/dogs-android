package com.dogs.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val url: String?, val width: Int?, val height: Int?) : Parcelable
