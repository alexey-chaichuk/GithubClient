package ru.chay.githubclient.ui.util

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.Timer
import kotlin.concurrent.timerTask

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer = Timer()

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            timer.cancel()
            val sleep = when (editable?.length) {
                1 -> 1000L
                2, 3 -> 700L
                4, 5 -> 500L
                else -> 300L
            }
            timer = Timer()
            timer.schedule(timerTask {
                afterTextChanged.invoke(editable.toString())
            }, sleep)
        }
    })
}

fun View.showSnackbar(
    @StringRes stringRes: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
) {
    Snackbar.make(this, stringRes, duration).show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun Activity.showSystemMessage(text: String, longDuration: Boolean = false) =
    Toast.makeText(this, text, if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()

fun Fragment.showSystemMessage(text: String, longDuration: Boolean = false) {
    activity?.showSystemMessage(text, longDuration)
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}