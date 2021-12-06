package com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote

import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.model.ShortUrlResponseModel
import kotlinx.coroutines.flow.Flow

interface ShortenerRemoteDatasource {
    suspend fun shortUrl(shortUrlRequestModel: ShortUrlRequestModel): Flow<ShortUrlResponseModel>
}
