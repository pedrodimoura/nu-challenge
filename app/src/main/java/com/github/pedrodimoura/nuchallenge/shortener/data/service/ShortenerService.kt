package com.github.pedrodimoura.nuchallenge.shortener.data.service

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ShortenerService {

    @POST("alias")
    suspend fun shortUrl(@Body shortUrlRequestModel: ShortUrlRequestModel): ShortUrlResponseModel
}
