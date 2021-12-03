package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.impl

import com.github.pedrodimoura.nuchallenge.shortener.data.dao.ShortenerDao
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ShortenerLocalDatasourceImpl @Inject constructor(
    private val shortenerDao: ShortenerDao,
) : ShortenerLocalDatasource {
    override suspend fun save(shortUrlLocalModel: ShortUrlLocalModel) =
        shortenerDao.save(shortUrlLocalModel)

    override suspend fun getRecentlyShortenedUrls(): Flow<List<ShortUrlLocalModel>> =
        shortenerDao.getRecentlyShortenedUrls()
}
