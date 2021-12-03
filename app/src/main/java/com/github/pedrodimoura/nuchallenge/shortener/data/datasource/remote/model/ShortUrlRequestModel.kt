package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class ShortUrlRequestModel(
    @SerializedName("url")
    val url: String,
)
