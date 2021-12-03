package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlResponseModel
import kotlinx.coroutines.flow.Flow

interface ShortenerRemoteDatasource {
    suspend fun shortUrl(shortUrlRequestModel: ShortUrlRequestModel): Flow<ShortUrlResponseModel>
}
