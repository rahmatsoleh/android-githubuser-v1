package com.example.submission1bfa.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubUser
import com.example.submission1bfa.data.response.GithubUserResponse
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val KEYWORD = "rahmatsoleh"
    }

    init {
        getListGithubUser(KEYWORD)
    }

    private val _githubUsers = MutableLiveData<ArrayList<GithubUser>>()
    val githubUsers: LiveData<ArrayList<GithubUser>> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListGithubUser(keyword: String) {
        _isLoading.value = true
        val listGithubUser = ArrayList<GithubUser>()
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
                            val githubUser = GithubUser(userItems[i].login, userItems[i].avatarUrl)
                            listGithubUser.add(githubUser)
                        }

                        _githubUsers.value = listGithubUser
                        _isLoading.value = false
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }

                Log.d("listGithubUser1", "${listGithubUser}")
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        } )

    }
}