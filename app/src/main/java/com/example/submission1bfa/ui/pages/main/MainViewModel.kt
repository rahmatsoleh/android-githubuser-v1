package com.example.submission1bfa.ui.pages.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.data.response.GithubUserResponse
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    var status = MutableLiveData<Boolean?>()

    private val _githubUsers = MutableLiveData<ArrayList<GithubUsers>>()
    val githubUsers: LiveData<ArrayList<GithubUsers>> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    fun getListGithubUser(keyword: String) {
        _isLoading.value = true
        val listGithubUsers = ArrayList<GithubUsers>()
        val client = ApiConfig.getApiService().getGithubUsers(keyword)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val userItems = responseBody.items

                        for (i in userItems.indices) {
                            val githubUsers = GithubUsers(userItems[i].login, userItems[i].avatarUrl, userItems[i].type)
                            listGithubUsers.add(githubUsers)
                        }

                        _githubUsers.value = listGithubUsers
                        _isLoading.value = false
                        _totalCount.value = responseBody.totalCount
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                    status.value = true
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                status.value = true
            }
        } )

    }
}