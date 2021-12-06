package com.github.pedrodimoura.nuchallenge.shortener.presentation.activity.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.pedrodimoura.nuchallenge.R
import kotlinx.coroutines.FlowPreview
import org.hamcrest.Matchers.allOf

@FlowPreview
class ShortenerTestResult {
    fun checkIfSnackWasShownWithText(text: String): ShortenerTestResult {
        onView(allOf(withId(com.google.android.material.R.id.snackbar_text)))
            .check(matches(withText(text)))
        return this
    }

    fun checkIfInvalidUrlMessageWasShown(errorMessage: String): ShortenerTestResult {
        onView(withId(R.id.editTextUrlToShort))
            .check(matches(hasErrorText(errorMessage)))
        return this
    }
}
