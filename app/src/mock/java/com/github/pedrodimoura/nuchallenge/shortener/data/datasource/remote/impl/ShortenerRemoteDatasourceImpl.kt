package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.impl

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.ShortenerRemoteDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.Links
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model.ShortUrlResponseModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShortenerRemoteDatasourceImpl @Inject constructor() : ShortenerRemoteDatasource {

    override suspend fun shortUrl(
        shortUrlRequestModel: ShortUrlRequestModel
    ): Flow<ShortUrlResponseModel> {
        val randomFakeString = Random.nextInt(9999).toString()
        return flow {
            emit(
                ShortUrlResponseModel(
                    randomFakeString,
                    Links(shortUrlRequestModel.url, randomFakeString)
                )
            )
        }
    }
}


