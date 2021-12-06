package com.github.pedrodimoura.nuchallenge.common.di

import com.github.pedrodimoura.nuchallenge.BuildConfig
import com.github.pedrodimoura.nuchallenge.common.data.service.HttpClient
import com.github.pedrodimoura.nuchallenge.common.data.service.retrofit.RetrofitClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ServiceDependencyModule {

    @Provides
    fun providesClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): HttpClient.RetrofitClient =
        RetrofitClientImpl(BuildConfig.BASE_URL, okHttpClient, gsonConverterFactory)
}
