package com.example.submission1bfa.data.response

import com.google.gson.annotations.SerializedName

data class ListReposResponse(

	@field:SerializedName("ListReposResponse")
	val listReposResponse: List<ListReposResponseItem>
)

data class ListReposResponseItem(

	@field:SerializedName("forks")
	val forks: Int,

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String
)
