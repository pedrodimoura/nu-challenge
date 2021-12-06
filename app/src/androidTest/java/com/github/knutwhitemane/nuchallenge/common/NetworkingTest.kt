package com.github.knutwhitemane.nuchallenge.common

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before

private val mockWebServer = MockWebServer()
val testUrl = mockWebServer.url("/").toString()

private const val OK_HTTP_IR_NAME = "okhttp"

abstract class NetworkingTest : BaseTest() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    override fun setup() {
        super.setup()
        IdlingRegistry.getInstance()
            .register(OkHttp3IdlingResource.create(OK_HTTP_IR_NAME, okHttpClient))
    }

    fun setDispatcher(dispatcher: Dispatcher) {
        mockWebServer.dispatcher = dispatcher
    }
}
