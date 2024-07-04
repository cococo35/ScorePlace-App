package com.scoreplace.hanple.network


import com.scoreplace.hanple.data.remote.AddressRemoteDataResource
import com.scoreplace.hanple.data.remote.WeatherRemoteDataResource
import com.scoreplace.hanple.data.remote.CongestionRemoteDataResource
import com.scoreplace.hanple.data.remote.DustRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class address

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class dust

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class weather

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class congestion


    private const val BASE_URL_ADDRESS = "https://dapi.kakao.com"
    private const val BASE_URL_CONGESTION = "http://openapi.seoul.go.kr:8088/"
    private const val BASE_URL_DUST = "https://api.openweathermap.org/data/2.5/air_pollution/"
    private const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"

    private val logging = HttpLoggingInterceptor().apply {
        // 요청과 응답의 본문 내용까지 로그에 포함
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    @address
    fun provideAddressOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(150, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .writeTimeout(150, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()


    @Provides
    @address
    fun provideAddressRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @address
    fun provideAddressSearch(@address retrofit: Retrofit): AddressRemoteDataResource {
        return retrofit.create(AddressRemoteDataResource::class.java)
    }

    @Provides
    @congestion
    fun provideCongestionOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(150, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .writeTimeout(150, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()


    @Provides
    @congestion
    fun provideCongestionRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_CONGESTION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @congestion
    fun provideCongestionSearch(@congestion retrofit: Retrofit): CongestionRemoteDataResource {
        return retrofit.create(CongestionRemoteDataResource::class.java)
    }

    @Provides
    @dust
    fun provideDustOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(150, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .writeTimeout(150, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()


    @Provides
    @dust
    fun provideDustRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_DUST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @dust
    fun provideDustSearch(@dust retrofit: Retrofit): DustRemoteDataSource {
        return retrofit.create( DustRemoteDataSource::class.java)
    }

    @Provides
    @weather
    fun provideWeatherOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(150, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .writeTimeout(150, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()


    @Provides
    @weather
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @weather
    fun provideWeatherSearch(@weather retrofit: Retrofit): WeatherRemoteDataResource {
        return retrofit.create(WeatherRemoteDataResource::class.java)
    }

}
