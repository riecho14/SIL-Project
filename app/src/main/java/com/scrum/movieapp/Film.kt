package com.scrum.movieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film (
    val name: String,
    val desc: String,
    val photo: String
): Parcelable