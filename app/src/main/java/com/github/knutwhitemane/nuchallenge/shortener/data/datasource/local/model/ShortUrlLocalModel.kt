package com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

const val TABLE_NAME = "short_url"
private const val ALIAS = "alias"

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(ALIAS)]
)
data class ShortUrlLocalModel(
    @PrimaryKey
    val alias: String,
    val originalUrl: String,
    val shortUrl: String,
    val createdAt: Long,
)
