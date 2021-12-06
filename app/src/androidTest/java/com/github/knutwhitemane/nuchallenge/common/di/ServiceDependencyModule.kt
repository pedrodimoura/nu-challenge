package com.github.knutwhitemane.nuchallenge.common.di

import android.util.Log
import com.github.knutwhitemane.nuchallenge.common.data.service.HttpClient
import com.github.knutwhitemane.nuchallenge.common.data.service.retrofit.RetrofitClientImpl
import com.github.knutwhitemane.nuchallenge.common.testUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingInterceptor

private const val HTTP_LOGGING_KEY = "OkHttpClient_body"

@Module
@InstallIn(SingletonComponent::class)
object ServiceDependencyModule {
    @LoggingInterceptor
    @Provides
    fun providesHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor {
        Log.d(HTTP_LOGGING_KEY, it)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesClient(
        @LoggingInterceptor httpLoggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): HttpClient.RetrofitClient =
        RetrofitClientImpl(testUrl, okHttpClient, gsonConverterFactory)
}