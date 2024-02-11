package com.example.submission1bfa.ui.pages.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
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

                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _detailUser.value = responseBody
                        Log.d("DataDetailResponse", responseBody.toString())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    status.value = true
                }

            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                status.value = true
                _isLoading.value = false
            }
        })

    }
}