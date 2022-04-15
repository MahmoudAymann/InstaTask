package com.instabug.task.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

object InstaUtil {
    fun tintMenuIcon(context: Context, item: MenuItem, @ColorRes color: Int) {
        val normalDrawable: Drawable = item.icon
        val wrapDrawable = DrawableCompat.wrap(normalDrawable)
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, color))
        item.icon = wrapDrawable
    }
}