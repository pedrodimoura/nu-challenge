package com.github.knutwhitemane.nuchallenge.shortener.domain.repository

import com.github.knutwhitemane.nuchallenge.shortener.domain.model.ShortUrlModel
import kotlinx.coroutines.flow.Flow

interface ShortenerRepository {
    suspend fun short(url: String): Flow<ShortUrlModel>
    suspend fun save(shortUrlModel: ShortUrlModel)
    suspend fun getRecentlyShortenedUrls(): Flow<List<ShortUrlModel>>
}
