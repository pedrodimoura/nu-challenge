package com.github.pedrodimoura.nuchallenge.shortener.data.repository

import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.pedrodimoura.nuchallenge.shortener.domain.repository.ShortenerRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShortenerRepositoryImpl @Inject constructor(
    private val shortenerLocalDatasource: ShortenerLocalDatasource,
) : ShortenerRepository {
    override fun short(url: String) {
        shortenerLocalDatasource.save(ShortUrlLocalModel("aiai papai", "aiai papai", "aiai papai"))
    }

    override fun getRecentShortenUrls(): Flow<List<ShortUrlModel>> =
        shortenerLocalDatasource.getRecentlyShortenedUrls().map {
            it.map { shortUrlLocalModel -> shortUrlLocalModel.toDomain() }
        }

    private fun ShortUrlLocalModel.toDomain() =
        ShortUrlModel(
            this.alias,
            this.originalUrl,
            this.shortUrl
        )
}
