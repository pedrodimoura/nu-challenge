package com.github.pedrodimoura.nuchallenge.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.pedrodimoura.nuchallenge.shortener.data.dao.ShortenerDao
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.model.ShortUrlLocalModel

@Database(entities = [ShortUrlLocalModel::class], version = 1)
abstract class NuChallengeDatabase : RoomDatabase() {
    abstract fun getShortenerDao(): ShortenerDao
}