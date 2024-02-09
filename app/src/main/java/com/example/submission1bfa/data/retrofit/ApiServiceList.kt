package com.example.submission1bfa.data.retrofit

import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.data.response.GithubUserResponse
import com.example.submission1bfa.data.response.UserReposResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceList {
    @GET("search/users?")
    fun getGithubUsers(
        @Query("q") keyword: String
    ): Call<GithubUserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/repos")
    fun getReposUser(
        @Path("login") login: String
    ): Call<ArrayList<UserReposResponse>>

    @GET("users/{login}/followers")
    fun getFollowerUser(
        @Path("login") login: String
    ): Call<ArrayList<GithubUsers>>

    @GET("users/{login}/following")
    fun getFollowingUser(
        @Path("login") login: String
    ): Call<ArrayList<GithubUsers>>
}