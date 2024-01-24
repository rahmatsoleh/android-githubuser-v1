package com.example.submission1bfa.data.retrofit

import com.example.submission1bfa.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceList {
    @Headers("Authorization: token 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")
    @GET("search/users?")
    fun getGithubUsers(
        @Query("q") keyword: String
    ): Call<GithubUserResponse>
}