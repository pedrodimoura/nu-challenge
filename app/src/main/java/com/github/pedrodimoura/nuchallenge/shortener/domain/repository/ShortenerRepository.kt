package com.github.pedrodimoura.nuchallenge.shortener.domain.repository

import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel
import kotlinx.coroutines.flow.Flow

interface ShortenerRepository {
    fun short(url: String)
    fun getRecentShortenUrls(): Flow<List<ShortUrlModel>>
}
