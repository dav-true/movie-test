package com.example.movieapp.di

import com.example.movieapp.BuildConfig
import com.example.movieapp.api.Constants
import com.example.movieapp.api.MovieApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


const val connectTimeout: Long = 15
const val readTimeout: Long = 15

val netModule = module {
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideInstagramApiService(get(), get()) }
}


fun provideGson() = GsonBuilder().setLenient().create()!!


fun provideOkHttpClient() = OkHttpClient.Builder()
    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
    .readTimeout(readTimeout, TimeUnit.SECONDS)
    .addInterceptor(Interceptor {
        val request =
            it.request().newBuilder().addHeader("Authorization", BuildConfig.ApiKey).build()
        it.proceed(request)
    })
    .build()

fun provideInstagramApiService(okHttpClient: OkHttpClient, gson: Gson) =
    Retrofit.Builder().baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .client(okHttpClient)
        .build()
        .create<MovieApiService>()



