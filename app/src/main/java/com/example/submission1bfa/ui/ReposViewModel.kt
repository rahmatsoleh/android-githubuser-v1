package com.example.submission1bfa.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubRepos
import com.example.submission1bfa.data.response.ListReposResponse
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
        client.enqueue(object: Callback<ArrayList<ListReposResponse>> {
//            override fun onResponse(
//                call: Call<List<ListReposResponse>>,
//                response: Response<ListReposResponse>
//            ) {
//
//            }

            override fun onFailure(call: Call<ArrayList<ListReposResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureCuy: ${t.message}")
                status.value = true
            }

            override fun onResponse(
                call: Call<ArrayList<ListReposResponse>>,
                response: Response<ArrayList<ListReposResponse>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "SIZE: ${responseBody.size.toString()}")
                        Log.d(TAG, "DATA[0]: ${responseBody[0]}") // Hasilnya
                    }

//                    if (responseBody != null) {
//                        val reposItems = responseBody.listReposResponse
//
//                        if (reposItems != null) {
//                            for (i in reposItems.indices) {
//                                val reposUser = GithubRepos(reposItems[i]?.name.toString(), reposItems[i]?.htmlUrl.toString(), reposItems[i]?.description.toString(),
//                                    reposItems[i]?.forks?.toInt() ?: 0, reposItems[i]?.stargazersCount?.toInt() ?: 0)
//                                listRepositoryGithub.add(reposUser)
//                            }
//                        }
//                    }
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