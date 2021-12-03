package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.impl

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.ShortenerRemoteDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlResponseModel
import com.github.pedrodimoura.nuchallenge.shortener.data.service.ShortenerService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShortenerRemoteDatasourceImpl @Inject constructor(
    private val shortenerService: ShortenerService,
) : ShortenerRemoteDatasource {

    override suspend fun shortUrl(
        shortUrlRequestModel: ShortUrlRequestModel
    ): Flow<ShortUrlResponseModel> =
        flow { emit(shortenerService.shortUrl(shortUrlRequestModel)) }
}
