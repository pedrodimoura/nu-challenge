package com.github.knutwhitemane.nuchallenge.shortener.data.repository

import com.github.knutwhitemane.nuchallenge.common.domain.exception.CommunicationException
import com.github.knutwhitemane.nuchallenge.common.domain.exception.UnknownException
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.ShortenerRemoteDatasource
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.model.ShortUrlRequestModel
import com.github.knutwhitemane.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.knutwhitemane.nuchallenge.shortener.domain.repository.ShortenerRepository
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ShortenerRepositoryImpl @Inject constructor(
    private val shortenerLocalDatasource: ShortenerLocalDatasource,
    private val shortenerRemoteDatasource: ShortenerRemoteDatasource,
) : ShortenerRepository {
    override suspend fun short(url: String): Flow<ShortUrlModel> {
        return shortenerRemoteDatasource.shortUrl(ShortUrlRequestModel(url))
            .map { ShortUrlModel(it.alias, it.links.self, it.links.short) }
            .catch { handleShortenerRepositoryException(it) }
    }

    override suspend fun save(shortUrlModel: ShortUrlModel) {
        runCatching {
            shortenerLocalDatasource.save(
                ShortUrlLocalModel(
                    shortUrlModel.alias,
                    shortUrlModel.originalUrl,
                    shortUrlModel.shortUrl,
                    Date().time
                )
            )
        }.onFailure { handleShortenerRepositoryException(it) }
    }

    override suspend fun getRecentlyShortenedUrls(): Flow<List<ShortUrlModel>> =
        shortenerLocalDatasource.getRecentlyShortenedUrls().map {
            it.map { shortUrlLocalModel -> shortUrlLocalModel.toDomain() }
        }.catch { handleShortenerRepositoryException(it) }

    private fun ShortUrlLocalModel.toDomain() =
        ShortUrlModel(
            this.alias,
            this.originalUrl,
            this.shortUrl
        )

    private fun handleShortenerRepositoryException(throwable: Throwable) {
        throw when (throwable) {
            is IOException -> CommunicationException()
            else -> UnknownException()
        }
    }
}
