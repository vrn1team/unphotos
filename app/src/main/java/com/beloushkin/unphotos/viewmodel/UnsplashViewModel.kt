package com.beloushkin.unphotos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.beloushkin.unphotos.model.Photo
import com.beloushkin.unphotos.model.UnsplashApiService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashViewModel(application: Application):AndroidViewModel(application)
    ,Callback<List<Photo>>
{

    val photos by lazy { MutableLiveData<List<Photo>>() }
    val loadError by lazy {MutableLiveData<Boolean>()}
    val isLoading by lazy { MutableLiveData<Boolean>()}

    private val apiService = UnsplashApiService()

    fun refresh() {
        isLoading.value = true
        getPhotos()
    }

    fun getPhotos() {
        val call:Call<List<Photo>> = apiService.getPhotos()
        call.enqueue(this)
    }

    override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
        t.printStackTrace()
        isLoading.value = false
        loadError.value = true
    }

    override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
        if (response.isSuccessful) {
            photos.value = response.body()
            isLoading.value = false
            loadError.value = false
        } else {
            Log.d("M_UnsplashViewModel", response.errorBody().toString())
            isLoading.value = false
            loadError.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}