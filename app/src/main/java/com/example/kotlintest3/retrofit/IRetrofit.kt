package com.example.kotlintest3.retrofit

import com.example.kotlintest3.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {


    //https://www.unsplash.com/search <<베이직 url>> /photos/?query="searchterm부분"
    @GET(API.SEARCH_PHOTO)
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement> //리턴 부분 Call Json으로

    @GET(API.SEARCH_USERS)
    fun searchUsers (@Query("query") searchTerm: String) : Call<JsonElement>
}