package com.example.submission1bfa.data.retrofit

import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.data.response.GithubUserResponse
import com.example.submission1bfa.data.response.ListReposResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceList {
//    @Headers("Authorization: token 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")
    @GET("search/users?")
    fun getGithubUsers(
        @Query("q") keyword: String
    ): Call<GithubUserResponse>

//    @Headers("Authorization: token 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")
    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/repos")
    fun getReposUser(
        @Path("login") login: String
    ): Call<ArrayList<ListReposResponse>>
}