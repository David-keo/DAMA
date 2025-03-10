package com.cguerrero.colorsmemorize.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Color(
    val name: String,
    val pronunciation: String,
    val imageResId: Int,
    val audioResId: Int
) : Parcelable
