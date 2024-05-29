package com.android.hanple.network

import com.android.hanple.Weather.WeatherRemote
import com.android.hanple.data.congestion.remote.CongestionRemote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CongestionRetrofit {
    private val logging = HttpLoggingInterceptor().apply {
        // 요청과 응답의 본문 내용까지 로그에 포함
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val BASE_URL = "http://openapi.seoul.go.kr:8088/"
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
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


    val search: CongestionRemote by lazy {
        retrofit.create(CongestionRemote::class.java)
    }
}



