package com.github.knutwhitemane.nuchallenge.shortener.presentation.activity.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.knutwhitemane.nuchallenge.R
import com.github.knutwhitemane.nuchallenge.shortener.presentation.adapter.ShortenedUrlsViewHolder
import kotlinx.coroutines.FlowPreview

@FlowPreview
class ShortenerTestRobot {
    fun typeUrlOnEditText(text: String): ShortenerTestRobot {
        onView(withId(R.id.editTextUrlToShort))
            .perform(typeText(text))
        return this
    }

    fun clickShortButton(): ShortenerTestRobot {
        onView(withId(R.id.btnShortUrl))
            .perform(click())
        return this
    }

    fun clickOnRecyclerViewListAt(atPosition: Int): ShortenerTestRobot {
        onView(withId(R.id.rvRecentlyShortenedUrls))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShortenedUrlsViewHolder>(
                    atPosition,
                    click()
                )
            )
        return this
    }

    fun andFinally(): ShortenerTestResult = ShortenerTestResult()
}
