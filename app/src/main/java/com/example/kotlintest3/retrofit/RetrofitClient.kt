package com.example.kotlintest3.retrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.kotlintest3.App
import com.example.kotlintest3.utils.API
import com.example.kotlintest3.utils.Constants.TAG
import com.example.kotlintest3.utils.isJsonArray
import com.example.kotlintest3.utils.isJsonObject
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


// 싱글턴(메모리 하나만 씀)
object RetrofitClient {
    // 레트로핏 클라이언트 선언

    private var retrofitClient: Retrofit? = null //이건 옵션

    
    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")


        //Okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient - log() called / message: $message")

                when{
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else ->{
                        try{
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception){
                            Log.d(TAG, message)
                        }
                    }
                }

            }
        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        //위에서 설정한 인터셉터를 okhttp 클라에 추가
        client.addInterceptor(loggingInterceptor)


        // 기본 파라매터 넣기
        val baseParameterInterceptor : Interceptor = (object: Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "RetrofitClient - intercept() called")
                //오리지날 리퀘스트
                val originalRequest = chain.request()

                // ? client_id = 어쩌구 넣기 파트다
                //쿼리 파라메터 추가하기 client id 추가
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                val finalRequest = originalRequest.newBuilder().url(addedUrl).method(originalRequest.method, originalRequest.body).build()

                val response = chain.proceed(finalRequest)

                if(response.code != 200){

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(App.instance, "${response.code} 에러 입니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                return  response
            }
        })






        //위에서 설정한 기본 파라메터 인터셉터를 okhttp 클라에 추가
        client.addInterceptor(baseParameterInterceptor)

        // 커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)



        if(retrofitClient == null){

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                    //위에서 설정한 클라이언트로 레트로핏 클라이언트 설정
                .client(client.build())

                .build()
        }
        return retrofitClient
    }
}