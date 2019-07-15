package com.beloushkin.unphotos.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UnsplashApi {

    @GET("photos")
    abstract fun getPhotos(): Call<List<Photo>>

    @GET("photos/{id}")
    abstract fun getPhoto(@Path("id") photoId: String): Call<Photo>
}