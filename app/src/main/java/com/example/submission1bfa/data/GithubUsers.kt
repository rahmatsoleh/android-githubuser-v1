@file:Suppress("DEPRECATED_ANNOTATION")

package com.example.submission1bfa.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUsers(
    val login: String,
    val avatar_url: String,
    val type: String
) : Parcelable
