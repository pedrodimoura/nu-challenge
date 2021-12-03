package com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class ShortUrlResponseModel(
    @SerializedName("alias")
    val alias: String,
    @SerializedName("_links")
    val links: Links
)
