package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import kotlinx.coroutines.flow.Flow

interface ShortenerLocalDatasource {

    suspend fun save(shortUrlLocalModel: ShortUrlLocalModel)

    suspend fun getRecentlyShortenedUrls(): Flow<List<ShortUrlLocalModel>>

}
