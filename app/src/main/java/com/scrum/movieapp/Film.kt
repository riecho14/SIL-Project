package com.scrum.movieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val description: String? = "",
    val image: String? = "",
    val title: String? = "",
    val genre: String? = "",
    val actors: String? = "",
    val price: String? = ""
) : Parcelable