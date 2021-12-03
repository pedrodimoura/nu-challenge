package com.github.pedrodimoura.nuchallenge.shortener.presentation.state

import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

sealed class ShortenerUIState {
    object Ready : ShortenerUIState()

    object FetchingRecentlyShortenedUrls : ShortenerUIState()
    data class RecentlyShortenedUrlsFetched(
        val recentlyShortened: List<ShortUrlModel>
    ) : ShortenerUIState()

    object ShortingUrl : ShortenerUIState()
    object UrlShorted : ShortenerUIState()

    data class Failure(val message: String) : ShortenerUIState()
}
