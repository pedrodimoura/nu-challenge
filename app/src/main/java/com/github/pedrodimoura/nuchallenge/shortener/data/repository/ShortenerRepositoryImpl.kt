package com.github.pedrodimoura.nuchallenge.shortener.data.repository

import com.github.pedrodimoura.nuchallenge.common.domain.exception.CommunicationException
import com.github.pedrodimoura.nuchallenge.common.domain.exception.UnknownException
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.pedrodimoura.nuchallenge.shortener.domain.repository.ShortenerRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ShortenerRepositoryImpl @Inject constructor(
    private val shortenerLocalDatasource: ShortenerLocalDatasource,
) : ShortenerRepository {
    override suspend fun short(url: String) {
        shortenerLocalDatasource.save(ShortUrlLocalModel("aiai papai", "aiai papai", "aiai papai"))
    }

    override suspend fun getRecentShortenUrls(): Flow<List<ShortUrlModel>> =
        shortenerLocalDatasource.getRecentlyShortenedUrls().map {
            it.map { shortUrlLocalModel -> shortUrlLocalModel.toDomain() }
        }.catch {
            throw handleShortenerRepositoryException(it)
        }

    private fun ShortUrlLocalModel.toDomain() =
        ShortUrlModel(
            this.alias,
            this.originalUrl,
            this.shortUrl
        )

    private fun handleShortenerRepositoryException(throwable: Throwable): Throwable {
        return when (throwable) {
            is IOException -> CommunicationException()
            else -> UnknownException()
        }
    }
}
