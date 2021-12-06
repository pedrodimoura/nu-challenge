package com.github.knutwhitemane.nuchallenge.common

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    val hiltAndroidRule: HiltAndroidRule by lazy { HiltAndroidRule(this) }

    val applicationContext: Context by lazy { ApplicationProvider.getApplicationContext() }

    @Before
    open fun setup() {
        hiltAndroidRule.inject()
    }

    fun readStringFile(path: String): String {
        val assetManager = applicationContext.assets
        val inputStream = assetManager.open(path)
        val readerBuffer = ByteArray(inputStream.available())

        inputStream.read(readerBuffer)
        inputStream.close()

        return String(readerBuffer)
    }

}