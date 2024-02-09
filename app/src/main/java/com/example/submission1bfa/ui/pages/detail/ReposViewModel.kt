package com.example.submission1bfa.ui.pages.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubRepos
import com.example.submission1bfa.data.response.UserReposResponse
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReposViewModel: ViewModel() {

    companion object {
        private const val TAG = "ReposViewModel"
    }

    private val _reposUsers = MutableLiveData<ArrayList<GithubRepos>>()
    val reposUsers: LiveData<ArrayList<GithubRepos>> = _reposUsers

    private  val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var status = MutableLiveData<Boolean?>()

    fun getListRepository(login: String) {
        _isLoading.value = true
        val listRepositoryGithub = ArrayList<GithubRepos>()
        val client = ApiConfig.getApiService().getReposUser(login)
        client.enqueue(object: Callback<ArrayList<UserReposResponse>> {

            override fun onFailure(call: Call<ArrayList<UserReposResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureCuy: ${t.message}")
                status.value = true
            }

            override fun onResponse(
                call: Call<ArrayList<UserReposResponse>>,
                response: Response<ArrayList<UserReposResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        for (i in responseBody.indices) {
                            val reposUser = GithubRepos(responseBody[i]?.name.toString(), responseBody[i]?.description.toString(),
                                responseBody[i]?.forks?.toInt() ?: 0, responseBody[i]?.stargazersCount?.toInt() ?: 0, responseBody[i]?.htmlUrl.toString())
                            listRepositoryGithub.add(reposUser)
                        }
                    }
                    _isLoading.value = false
                    _reposUsers.value = listRepositoryGithub
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onResponseNotSuccess: ${response.message()}")
                    status.value = true
                }
            }
        })
    }
}