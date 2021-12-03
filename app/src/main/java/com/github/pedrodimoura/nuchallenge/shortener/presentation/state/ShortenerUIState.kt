package com.github.pedrodimoura.nuchallenge.shortener.presentation.state

import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

sealed class ShortenerUIState {
    object Idle : ShortenerUIState()
    object Loading : ShortenerUIState()
    object ShortingUrl : ShortenerUIState()
    object UrlShorted : ShortenerUIState()
    data class RecentlyShortenedUrlsFetched(
        val recentlyShortened: List<ShortUrlModel>
    ) : ShortenerUIState()

    data class Failure(val message: String) : ShortenerUIState()
}
