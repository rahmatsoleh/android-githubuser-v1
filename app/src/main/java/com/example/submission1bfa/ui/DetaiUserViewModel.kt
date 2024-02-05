package com.example.submission1bfa.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.data.response.GithubUserResponse
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetaiUserViewModel: ViewModel() {
    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    var status = MutableLiveData<Boolean?>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    fun getDetailGithubUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _isLoading.value = false
                        _detailUser.value = responseBody
                        Log.d("DataDetailResponse", responseBody.toString())
                    }
                } else {
                    _isLoading.value = false
                    Log.e(DetaiUserViewModel.TAG, "onFailure: ${response.message()}")
                    status.value = true
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetaiUserViewModel.TAG, "onFailure: ${t.message}")
                status.value = true
            }
        } )

    }
}