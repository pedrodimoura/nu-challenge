package com.github.pedrodimoura.nuchallenge.shortener.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.github.pedrodimoura.nuchallenge.databinding.ActivityShortenerBinding
import com.github.pedrodimoura.nuchallenge.shortener.presentation.state.ShortenerUIState
import com.github.pedrodimoura.nuchallenge.shortener.presentation.vm.ShortenerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShortenerActivity : AppCompatActivity() {

    private val binding: ActivityShortenerBinding by lazy {
        ActivityShortenerBinding.inflate(layoutInflater)
    }

    private val viewModel: ShortenerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.asLiveData().observe(this) { uiState ->
            when (uiState) {
                is ShortenerUIState.FetchingRecentlyShortenedUrls -> logcat("Loading")
                is ShortenerUIState.RecentlyShortenedUrlsFetched ->
                    logcat("Success: ${uiState.recentlyShortened}")
                is ShortenerUIState.Failure -> logcat("Failure ${uiState.message}")
                is ShortenerUIState.ShortingUrl -> logcat("Shorting Url")
                is ShortenerUIState.UrlShorted -> logcat("Url Shorted")
                else -> logcat("Idle State")
            }
        }

        viewModel.getRecentlyShortenedUrls()

        binding.btnShortUrl.setOnClickListener {
            // TODO: Get URL from TextView and Call short(url) from ViewModel
            viewModel.short("Teste")
        }
    }

    private fun logcat(message: String) = Log.d("nu_chg", message)
}