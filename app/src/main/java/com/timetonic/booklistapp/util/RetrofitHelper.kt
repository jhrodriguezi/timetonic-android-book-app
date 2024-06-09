package com.timetonic.booklistapp.util

import com.google.gson.GsonBuilder
import com.timetonic.booklistapp.BuildConfig
import com.timetonic.booklistapp.data.remote.TimetonicApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Helper object for initializing and providing a Retrofit instance configured for Timetonic API calls.
 */
object RetrofitHelper {

    private val retrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        val builder = Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .writeTimeout(0, TimeUnit.MILLISECONDS).writeTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()
        retrofit = builder.client(okHttpClient).build()
    }

    fun getTimetonicService(): TimetonicApi {
        return retrofit.create(TimetonicApi::class.java)
    }
}