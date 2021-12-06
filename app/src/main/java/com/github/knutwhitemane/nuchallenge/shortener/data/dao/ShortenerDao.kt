package com.github.knutwhitemane.nuchallenge.shortener.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import kotlinx.coroutines.flow.Flow

private const val DEFAULT_QUERY_LIMIT = 15

@Dao
interface ShortenerDao {

    @Insert(entity = ShortUrlLocalModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(shortenedUrlLocalModel: ShortUrlLocalModel)

    @Query("SELECT * FROM short_url ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentlyShortenedUrls(limit: Int = DEFAULT_QUERY_LIMIT): Flow<List<ShortUrlLocalModel>>
}
