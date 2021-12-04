package com.github.pedrodimoura.nuchallenge.common.presentation.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.pedrodimoura.nuchallenge.R

fun AppCompatActivity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    var view = this.currentFocus

    if (view == null)
        view = View(this)

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AppCompatActivity.disableTouchEvents() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun AppCompatActivity.enableTouchEvents() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Context.copyPlainTextToClipboard(label: String, plainText: String) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, plainText)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(this, R.string.sent_to_clipboard, Toast.LENGTH_SHORT).show()
}
