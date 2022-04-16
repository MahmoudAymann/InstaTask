package com.instabug.task.base

import android.app.Activity
import android.view.View
import android.widget.Toast

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun Activity.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()