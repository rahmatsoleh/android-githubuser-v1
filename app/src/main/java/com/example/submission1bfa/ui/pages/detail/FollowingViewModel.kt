package com.example.submission1bfa.ui.pages.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowingViewModel"
    }

    private val _followingUsers = MutableLiveData<ArrayList<GithubUsers>>()
    val followingUsers: LiveData<ArrayList<GithubUsers>> = _followingUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDataFollowing(login: String) {
        _isLoading.value = true
        val listDataFollowing = ArrayList<GithubUsers>()

        val client = ApiConfig.getApiService().getFollowingUser(login)

        client.enqueue(object : Callback<ArrayList<GithubUsers>> {
            override fun onResponse(
                call: Call<ArrayList<GithubUsers>>,
                response: Response<ArrayList<GithubUsers>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        for(i in responseBody.indices) {
                            val user = GithubUsers(responseBody[i]?.login.toString(), responseBody[i]?.avatar_url.toString(), responseBody[i].type.toString())
                            listDataFollowing.add(user)
                        }

                        _followingUsers.value = listDataFollowing
                    }
                } else {
                    Log.e(TAG, "on is not successfull: $response")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ArrayList<GithubUsers>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}