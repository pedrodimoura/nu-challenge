package com.github.pedrodimoura.nuchallenge.common.presentation.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.github.pedrodimoura.nuchallenge.R

fun Context.copyPlainTextToClipboard(label: String, plainText: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, plainText)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(this, R.string.sent_to_clipboard, Toast.LENGTH_SHORT).show()
}
