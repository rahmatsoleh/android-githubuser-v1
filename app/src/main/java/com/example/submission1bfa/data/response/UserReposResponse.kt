package com.example.submission1bfa.data.response

import com.google.gson.annotations.SerializedName

data class UserReposResponse(

    @field:SerializedName("forks")
    val forks: Int? = null,

    @field:SerializedName("stargazers_count")
    val stargazersCount: Int? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null
)
