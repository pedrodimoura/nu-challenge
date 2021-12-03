package com.github.pedrodimoura.nuchallenge.common.presentation.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    var view = this.currentFocus

    if (view == null)
        view = View(this)

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}