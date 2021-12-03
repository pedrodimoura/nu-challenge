package com.github.pedrodimoura.nuchallenge.shortener.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pedrodimoura.nuchallenge.app.di.DispatcherIO
import com.github.pedrodimoura.nuchallenge.shortener.domain.repository.ShortenerRepository
import com.github.pedrodimoura.nuchallenge.shortener.presentation.state.ShortenerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class ShortenerViewModel @Inject constructor(
    private val shortenerRepository: ShortenerRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShortenerUIState> =
        MutableStateFlow(ShortenerUIState.Ready)

    val uiState: StateFlow<ShortenerUIState> = _uiState

    fun getRecentlyShortenedUrls() {
        viewModelScope.launch {
            shortenerRepository.getRecentShortenUrls()
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.FetchingRecentlyShortenedUrls }
                .catch {
                    _uiState.value = ShortenerUIState.Failure(it.message.orEmpty())
                }
                .collect { _uiState.value = ShortenerUIState.RecentlyShortenedUrlsFetched(it) }
        }
    }

    fun short(url: String) {
        viewModelScope.launch {
            flowOf(shortenerRepository.short(url))
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.ShortingUrl }
                .catch { _uiState.value = ShortenerUIState.Failure(it.message.orEmpty()) }
                .flowOn(Dispatchers.Main)
                .collect { _uiState.value = ShortenerUIState.UrlShorted }
        }
    }
}
