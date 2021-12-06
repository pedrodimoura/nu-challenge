package com.github.knutwhitemane.nuchallenge.shortener.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.knutwhitemane.nuchallenge.app.di.DispatcherIO
import com.github.knutwhitemane.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.knutwhitemane.nuchallenge.shortener.domain.repository.ShortenerRepository
import com.github.knutwhitemane.nuchallenge.shortener.presentation.state.ShortenerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@FlowPreview
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
            shortenerRepository.getRecentlyShortenedUrls()
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.FetchingRecentlyShortenedUrls }
                .catch { throwable ->
                    _uiState.value = ShortenerUIState.Failure(throwable.message.orEmpty())
                }
                .flowOn(Dispatchers.Main)
                .collect { shortUrlModels ->
                    _uiState.value = ShortenerUIState.RecentlyShortenedUrlsFetched(shortUrlModels)
                    _uiState.value = ShortenerUIState.Ready
                }
        }
    }

    fun short(url: String) {
        viewModelScope.launch {
            shortenerRepository.short(url)
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.ShortingUrl }
                .catch { throwable ->
                    _uiState.value = ShortenerUIState.Failure(throwable.message.orEmpty())
                    _uiState.value = ShortenerUIState.Ready
                }
                .collect { shortUrlModel ->
                    _uiState.value = ShortenerUIState.UrlShorted(shortUrlModel)
                }
        }
    }

    fun save(shortUrlModel: ShortUrlModel) {
        viewModelScope.launch {
            flowOf(shortenerRepository.save(shortUrlModel))
                .flowOn(dispatcher)
                .onStart { _uiState.value = ShortenerUIState.SavingShortenedUrl }
                .catch { throwable ->
                    _uiState.value = ShortenerUIState.Failure(throwable.message.orEmpty())
                }
                .onCompletion { _uiState.value = ShortenerUIState.Ready }
                .collect { _uiState.value = ShortenerUIState.ShortenedUrlSaved }
        }
    }
}
