package com.android.hanple.network

import com.android.hanple.Weather.WeatherRemote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WeatherRetrofit {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val logging = HttpLoggingInterceptor().apply {
        // 요청과 응답의 본문 내용까지 로그에 포함
        level = HttpLoggingInterceptor.Level.BODY
    }
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    val search: WeatherRemote by lazy {
        retrofit.create(WeatherRemote::class.java)
    }
}