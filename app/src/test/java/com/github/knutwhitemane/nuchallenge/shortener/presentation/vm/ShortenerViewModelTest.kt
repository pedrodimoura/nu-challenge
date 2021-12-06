package com.github.knutwhitemane.nuchallenge.shortener.presentation.vm

import app.cash.turbine.test
import com.github.knutwhitemane.nuchallenge.common.domain.exception.CommunicationException
import com.github.knutwhitemane.nuchallenge.common.domain.exception.UnknownException
import com.github.knutwhitemane.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.knutwhitemane.nuchallenge.shortener.domain.repository.ShortenerRepository
import com.github.knutwhitemane.nuchallenge.shortener.presentation.state.ShortenerUIState
import com.github.knutwhitemane.nuchallenge.shortener.util.TestCoroutineRule
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@FlowPreview
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
    fun `StateFlow SHOULD emit Ready, FetchingRecentlyShortenedUrls, RecentlyShortenedUrlsFetched and Ready States WHEN getRecentShortenedUrls is invoked`() =
        runBlockingTest {
            coEvery { shortenerRepository.getRecentlyShortenedUrls() } returns flow { emit(emptyList()) }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(
                    ShortenerUIState.RecentlyShortenedUrlsFetched(emptyList()),
                    awaitItem()
                )
                assertEquals(ShortenerUIState.Ready, awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, FetchingRecentlyShortenedUrls and Failure States WHEN getRecentShortenedUrls throws UnknownException`() =
        runBlockingTest {
            coEvery {
                shortenerRepository.getRecentlyShortenedUrls()
            } returns flow { throw UnknownException() }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(ShortenerUIState.Failure("Unknown Error"), awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, FetchingRecentlyShortenedUrls and Failure States WHEN getRecentShortenedUrls throws CommunicationException`() =
        runBlockingTest {
            coEvery {
                shortenerRepository.getRecentlyShortenedUrls()
            } returns flow { throw CommunicationException() }

            shortenerViewModel.uiState.test {
                shortenerViewModel.getRecentlyShortenedUrls()
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.FetchingRecentlyShortenedUrls, awaitItem())
                assertEquals(ShortenerUIState.Failure("Communication Failure"), awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, ShortingUrl and UrlShortened States WHEN short is invoked`() =
        runBlockingTest {
            val expectedModel = ShortUrlModel("", "", "")
            coEvery { shortenerRepository.short(any()) } returns flow { emit(expectedModel) }

            shortenerViewModel.uiState.test {
                shortenerViewModel.short("http://www.google.com.br/")
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.ShortingUrl, awaitItem())
                assertEquals(ShortenerUIState.UrlShorted(expectedModel), awaitItem())
            }
        }

    @Test
    fun `StateFlow SHOULD emit Ready, SavingShortenedUrl, ShortenedUrlSaved and Ready States WHEN short is invoked`() =
        runBlockingTest {
            coEvery { shortenerRepository.save(any()) } just Runs

            shortenerViewModel.uiState.test {
                shortenerViewModel.save(ShortUrlModel("abc", "abc", "abc"))
                assertEquals(ShortenerUIState.Ready, awaitItem())
                assertEquals(ShortenerUIState.SavingShortenedUrl, awaitItem())
                assertEquals(ShortenerUIState.ShortenedUrlSaved, awaitItem())
                assertEquals(ShortenerUIState.Ready, awaitItem())
            }
        }
}
