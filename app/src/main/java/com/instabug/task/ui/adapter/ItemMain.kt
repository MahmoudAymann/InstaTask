package com.instabug.task.ui.adapter

import com.instabug.task.base.UiModel

@UiModel
data class ItemMain(
    val id: Int,
    val word: String,
    val count: Int
) {
    companion object {
        fun dummy() = listOf(
            ItemMain(1, "ss", 1),
            ItemMain(2, "aa", 12),
            ItemMain(3, "cvbcvb", 7),
            ItemMain(4, "cvb", 1),
            ItemMain(5, "reg", 4),
            ItemMain(6, "yukj", 20),
            ItemMain(7, "mm", 3),
            ItemMain(8, "m,,i", 8),
        )
    }
}