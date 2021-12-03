package com.github.pedrodimoura.nuchallenge.shortener.presentation.vm

import app.cash.turbine.test
import com.github.pedrodimoura.nuchallenge.common.domain.exception.CommunicationException
import com.github.pedrodimoura.nuchallenge.common.domain.exception.UnknownException
import com.github.pedrodimoura.nuchallenge.shortener.domain.repository.ShortenerRepository
import com.github.pedrodimoura.nuchallenge.shortener.presentation.state.ShortenerUIState
import com.github.pedrodimoura.nuchallenge.shortener.util.TestCoroutineRule
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShortenerViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val shortenerRepository: ShortenerRepository by lazy { mockk() }
    private val shortenerViewModel: ShortenerViewModel by lazy {
        ShortenerViewModel(shortenerRepository, testCoroutineRule.testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `StateFlow SHOULD emit Ready State WHEN getRecentShortenedUrls is not invoked`() =
        runBlockingTest {
            shortenerViewModel.uiState.test {
                assertEquals(ShortenerUIState.Ready, awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, Fetching and Success States WHEN getRecentShortenedUrls is invoked`() =
        runBlockingTest {
            coEvery { shortenerRepository.getRecentShortenUrls() } returns flow { emit(emptyList()) }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(
                    ShortenerUIState.RecentlyShortenedUrlsFetched(emptyList()),
                    awaitItem()
                )
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, Fetching and Failure States WHEN getRecentShortenedUrls throws UnknownException`() =
        runBlockingTest {
            coEvery {
                shortenerRepository.getRecentShortenUrls()
            } returns flow { throw UnknownException() }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(ShortenerUIState.Failure("Unknown Error"), awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, Fetching and Failure States WHEN getRecentShortenedUrls throws CommunicationException`() =
        runBlockingTest {
            coEvery {
                shortenerRepository.getRecentShortenUrls()
            } returns flow { throw CommunicationException() }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(ShortenerUIState.Failure("Communication Failure"), awaitItem())
            }
        }
}
