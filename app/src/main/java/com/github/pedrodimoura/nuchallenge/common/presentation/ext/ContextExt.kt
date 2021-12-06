package com.github.pedrodimoura.nuchallenge.common.presentation.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyPlainTextToClipboard(label: String, plainText: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, plainText)
    clipboardManager.setPrimaryClip(clipData)
}
