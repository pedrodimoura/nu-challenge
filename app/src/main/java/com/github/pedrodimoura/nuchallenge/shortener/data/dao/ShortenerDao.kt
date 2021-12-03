package com.github.pedrodimoura.nuchallenge.shortener.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ShortenerDao {

    @Insert(entity = ShortUrlLocalModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(shortenedUrlLocalModel: ShortUrlLocalModel)

    @Query("SELECT * FROM short_url")
    fun getRecentlyShortenedUrls(): Flow<List<ShortUrlLocalModel>>
}
