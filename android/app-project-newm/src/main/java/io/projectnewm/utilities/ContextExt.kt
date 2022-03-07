package io.projectnewm.utilities

import android.content.Context
import android.widget.Toast

fun Context.shortToast(value: String) {
    Toast.makeText(
        this,
        value,
        Toast.LENGTH_SHORT
    ).show()
}

fun Context.longToast(value: String) {
    Toast.makeText(
        this,
        value,
        Toast.LENGTH_LONG
    ).show()
}