package com.github.pedrodimoura.nuchallenge.shortener.presentation.state

import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

sealed class ShortenerUIState {
    object Ready : ShortenerUIState()

    object FetchingRecentlyShortenedUrls : ShortenerUIState()
    data class RecentlyShortenedUrlsFetched(
        val recentlyShortenedUrls: List<ShortUrlModel>
    ) : ShortenerUIState()

    object ShortingUrl : ShortenerUIState()

    data class UrlShorted(val shortUrlModel: ShortUrlModel) : ShortenerUIState()

    object SavingShortenedUrl : ShortenerUIState()
    object ShortenedUrlSaved : ShortenerUIState()

    data class Failure(val message: String) : ShortenerUIState()
}
