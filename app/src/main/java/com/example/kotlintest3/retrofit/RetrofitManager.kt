package com.example.kotlintest3.retrofit

import android.util.Log
import com.example.kotlintest3.utils.API
import com.example.kotlintest3.utils.Constants.TAG
import com.example.kotlintest3.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }

    //레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE,String) -> Unit){

        val term = searchTerm ?: "" //searchTerm이 비어있으면 ""를 넣어라
        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{

            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.raw()}")

                completion(RESPONSE_STATE.OKAY,response.raw().toString())
            }

            //응답 실패시시
           override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t" )

                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })

    }


}