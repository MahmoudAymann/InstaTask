package com.instabug.task.ui.adapter


data class ItemWord(
    val id: Int,
    val word: String,
    val count: Int
) {
    companion object {
        fun dummy() = listOf(
            ItemWord(1, "ss", 1),
            ItemWord(2, "aa", 12),
            ItemWord(3, "cvbcvb", 7),
            ItemWord(4, "cvb", 1),
            ItemWord(5, "reg", 4),
            ItemWord(6, "yukj", 20),
            ItemWord(7, "mm", 3),
            ItemWord(8, "m,,i", 8),
        )
    }
}