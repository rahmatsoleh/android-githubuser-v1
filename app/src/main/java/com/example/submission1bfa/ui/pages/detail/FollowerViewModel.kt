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

class FollowerViewModel: ViewModel() {
    companion object {
       private  const val TAG = "FollowerViewModel"
    }

    private val _followerUser = MutableLiveData<ArrayList<GithubUsers>>()
    val followerUser: LiveData<ArrayList<GithubUsers>> = _followerUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var status = MutableLiveData<Boolean?>()

    fun getFollowerUsers(login: String) {
        _isLoading.value = true
        val listFollowerUser = ArrayList<GithubUsers>()
        val client = ApiConfig.getApiService().getFollowerUser(login)

        client.enqueue(object: Callback<ArrayList<GithubUsers>> {
            override fun onResponse(
                call: Call<ArrayList<GithubUsers>>,
                response: Response<ArrayList<GithubUsers>>
            ) {

                if (response.isSuccessful) {
                    status.value = false
                    val responseBody = response.body()

                    if (responseBody != null) {
                        for (i in responseBody.indices) {
                            val followerUser = GithubUsers(responseBody[i]?.login.toString(), responseBody[i]?.avatar_url.toString(), responseBody[i]?.type.toString())
                            listFollowerUser.add(followerUser)
                        }

                        _followerUser.value = listFollowerUser
                    }
                } else {
                    status.value = true
                    Log.e(TAG, "ResponseNotSuccess : $response")
                }

                _isLoading.value = false
            }

            override fun onFailure(call: Call<ArrayList<GithubUsers>>, t: Throwable) {
                _isLoading.value = false
                status.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}