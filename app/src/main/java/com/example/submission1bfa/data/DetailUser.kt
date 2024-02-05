package com.example.submission1bfa.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUser(
    val login: String,
    val avatar_url: String,
    val url: String,
    val name: String,
    val company: String,
    val bio: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int
) : Parcelable
