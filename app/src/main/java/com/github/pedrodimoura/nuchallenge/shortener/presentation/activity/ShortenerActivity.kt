package com.github.pedrodimoura.nuchallenge.shortener.presentation.activity

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.github.pedrodimoura.nuchallenge.R
import com.github.pedrodimoura.nuchallenge.common.presentation.ext.hideKeyboard
import com.github.pedrodimoura.nuchallenge.databinding.ActivityShortenerBinding
import com.github.pedrodimoura.nuchallenge.shortener.presentation.state.ShortenerUIState
import com.github.pedrodimoura.nuchallenge.shortener.presentation.vm.ShortenerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class ShortenerActivity : AppCompatActivity() {

    private val binding: ActivityShortenerBinding by lazy {
        ActivityShortenerBinding.inflate(layoutInflater)
    }

    private val viewModel: ShortenerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeStateChanges()
        setupListeners()
        getRecentlyShortenedUrls()
    }

    private fun observeStateChanges() {
        viewModel.uiState.asLiveData().observe(this) { uiState ->
            when (uiState) {
                is ShortenerUIState.FetchingRecentlyShortenedUrls -> logcat("Loading")
                is ShortenerUIState.RecentlyShortenedUrlsFetched ->
                    logcat("Success: ${uiState.recentlyShortened}")
                is ShortenerUIState.Failure -> logcat("Failure ${uiState.message}")
                is ShortenerUIState.ShortingUrl -> logcat("Shorting Url")
                is ShortenerUIState.UrlShorted -> viewModel.save(uiState.shortUrlModel)
                is ShortenerUIState.ShortenedUrlSaved -> logcat("Shortened Url Saved")
                is ShortenerUIState.SavingShortenedUrl -> logcat("Saving shortened Url")
                else -> logcat("Idle State")
            }
        }
    }

    private fun setupListeners() {
        binding.btnShortUrl.setOnClickListener {
            hideKeyboard()
            with(binding.editTextUrlToShort) {
                val urlToShort = text
                when {
                    urlToShort.isNotEmpty() and Patterns.WEB_URL.matcher(urlToShort).matches() ->
                        viewModel.short(urlToShort.toString())
                    else -> error = getString(R.string.shortener_wrong_url_format)
                }
            }
        }
    }

    private fun getRecentlyShortenedUrls() = viewModel.getRecentlyShortenedUrls()

    private fun logcat(message: String) = Log.d("nu_chg", message)
}