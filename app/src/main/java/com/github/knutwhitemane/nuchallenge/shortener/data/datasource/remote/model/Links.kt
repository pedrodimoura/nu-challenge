package com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: String,
    @SerializedName("short")
    val short: String,
)
