package com.example.submission1bfa.data.retrofit

import com.example.submission1bfa.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceList {

    @GET("search/users?")
    fun getGithubUsers(
        @Query("q") keyword: String
    ): Call<GithubUserResponse>
}