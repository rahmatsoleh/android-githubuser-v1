package com.example.submission1bfa.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUser(
    val login: String,
    val avatar_url: String,
    val type: String
) : Parcelable
