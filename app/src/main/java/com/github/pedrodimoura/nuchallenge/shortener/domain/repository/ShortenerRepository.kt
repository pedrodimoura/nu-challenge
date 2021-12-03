package com.github.pedrodimoura.nuchallenge.shortener.domain.repository

import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel
import kotlinx.coroutines.flow.Flow

interface ShortenerRepository {
    suspend fun short(url: String)
    suspend fun getRecentShortenUrls(): Flow<List<ShortUrlModel>>
}
