package com.github.pedrodimoura.nuchallenge.shortener.domain.model

data class ShortUrlModel(
    val alias: String,
    val originalUrl: String,
    val shortUrl: String,
)
