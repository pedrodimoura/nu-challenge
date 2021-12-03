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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ShortenerViewModel @Inject constructor(
    private val shortenerRepository: ShortenerRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShortenerUIState> =
        MutableStateFlow(ShortenerUIState.Idle)

    val uiState: StateFlow<ShortenerUIState> = _uiState

    fun getRecentlyShortenedUrls() {
        viewModelScope.launch {
            shortenerRepository.getRecentShortenUrls()
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.Loading }
                .catch { _uiState.value = ShortenerUIState.Failure(it.message.orEmpty()) }
                .collect {
                    _uiState.value = ShortenerUIState.RecentlyShortenedUrlsFetched(it)
                    _uiState.value = ShortenerUIState.Idle
                }
        }
    }

    fun short(url: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.value = ShortenerUIState.ShortingUrl
            val result = runCatching {
                shortenerRepository.short(url)
            }
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    _uiState.value = ShortenerUIState.UrlShorted
                }.onFailure {
                    _uiState.value = ShortenerUIState.Failure(it.message.orEmpty())
                }
            }
        }
    }
}
