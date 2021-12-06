package com.github.pedrodimoura.nuchallenge.shortener.presentation.activity

import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.pedrodimoura.nuchallenge.R
import com.github.pedrodimoura.nuchallenge.common.presentation.ext.copyPlainTextToClipboard
import com.github.pedrodimoura.nuchallenge.common.presentation.ext.disableTouchEvents
import com.github.pedrodimoura.nuchallenge.common.presentation.ext.enableTouchEvents
import com.github.pedrodimoura.nuchallenge.common.presentation.ext.hideKeyboard
import com.github.pedrodimoura.nuchallenge.databinding.ActivityShortenerBinding
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel
import com.github.pedrodimoura.nuchallenge.shortener.presentation.adapter.ShortenedUrlsAdapter
import com.github.pedrodimoura.nuchallenge.shortener.presentation.state.ShortenerUIState
import com.github.pedrodimoura.nuchallenge.shortener.presentation.vm.ShortenerViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

private const val CB_LABEL_SHORT_URL = "Shortened Url"

@FlowPreview
@AndroidEntryPoint
class ShortenerActivity : AppCompatActivity() {

    private val binding: ActivityShortenerBinding by lazy {
        ActivityShortenerBinding.inflate(layoutInflater)
    }

    private val viewModel: ShortenerViewModel by viewModels()

    private val shortenedUrlsAdapter: ShortenedUrlsAdapter by lazy { ShortenedUrlsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        observeStateChanges()
        setupListeners()
        getRecentlyShortenedUrls()
    }

    private fun setupView() {
        setContentView(binding.root)
        with(binding.rvRecentlyShortenedUrls) {
            layoutManager = LinearLayoutManager(this@ShortenerActivity)
            adapter = shortenedUrlsAdapter
        }
    }

    private fun observeStateChanges() {
        viewModel.uiState.asLiveData().observe(this) { uiState ->
            when (uiState) {
                is ShortenerUIState.FetchingRecentlyShortenedUrls,
                is ShortenerUIState.ShortingUrl,
                is ShortenerUIState.SavingShortenedUrl -> setStateToBusy()
                is ShortenerUIState.RecentlyShortenedUrlsFetched ->
                    showRecentlyShortenedUrlsOnUI(uiState.recentlyShortenedUrls)
                is ShortenerUIState.Failure -> showSnackbar(uiState.message)
                is ShortenerUIState.UrlShorted -> viewModel.save(uiState.shortUrlModel)
                is ShortenerUIState.ShortenedUrlSaved -> clearInput()
                else -> setStateToIdle()
            }
        }
    }

    private fun clearInput() = binding.editTextUrlToShort.setText("")

    private fun showRecentlyShortenedUrlsOnUI(recentlyShortenedUrls: List<ShortUrlModel>) =
        shortenedUrlsAdapter.submitList(recentlyShortenedUrls) {
            binding.rvRecentlyShortenedUrls.scrollToPosition(0)
        }

    private fun setStateToBusy() {
        binding.pb.isVisible = true
        disableTouchEvents()
    }

    private fun setStateToIdle() {
        binding.pb.isVisible = false
        enableTouchEvents()
    }

    private fun setupListeners() {
        shortenedUrlsAdapter.onItemSelected { shortUrl ->
            copyPlainTextToClipboard(CB_LABEL_SHORT_URL, shortUrl)
            showSnackbar(getString(R.string.sent_to_clipboard))
        }
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

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}