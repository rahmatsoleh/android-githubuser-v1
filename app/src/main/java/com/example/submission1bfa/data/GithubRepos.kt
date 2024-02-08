package com.example.submission1bfa.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepos(
    val name: String,
    val html_url: String,
    val description: String,
    val forks: Int,
    val stargazers_count: Int
): Parcelable
