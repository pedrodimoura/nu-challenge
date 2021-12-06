package com.github.pedrodimoura.nuchallenge.shortener.presentation.activity

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.pedrodimoura.nuchallenge.R
import com.github.pedrodimoura.nuchallenge.common.NetworkingTest
import com.github.pedrodimoura.nuchallenge.shortener.presentation.activity.robot.ShortenerTestRobot
import dagger.hilt.android.testing.HiltAndroidTest
import java.net.HttpURLConnection
import kotlinx.coroutines.FlowPreview
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@FlowPreview
@RunWith(AndroidJUnit4::class)
class ShortenerActivityTest : NetworkingTest() {

    private val shortenerDispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val url = request.path.toString()
            return when {
                url.contains("alias") -> MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(readStringFile("responses/short_url_response.json"))
                else -> MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            }
        }
    }

    @Before
    override fun setup() {
        super.setup()
        super.setDispatcher(shortenerDispatcher)
    }

    @Test
    fun shouldSucceedWhenAValidUrlIsShortened() {
        launchActivity<ShortenerActivity>()
        ShortenerTestRobot()
            .typeUrlOnEditText("www.google.com.br")
            .clickShortButton()
            .clickOnRecyclerViewListAt(0)
            .andFinally()
            .checkIfSnackWasShownWithText(applicationContext.getString(R.string.sent_to_clipboard))
    }

    @Test
    fun shouldNotSucceedWhenAInvalidUrlIsShortened() {
        launchActivity<ShortenerActivity>()
        val expectedErrorMessage = applicationContext.getString(R.string.shortener_wrong_url_format)
        ShortenerTestRobot()
            .typeUrlOnEditText("1234")
            .clickShortButton()
            .andFinally()
            .checkIfInvalidUrlMessageWasShown(expectedErrorMessage)
    }
}
