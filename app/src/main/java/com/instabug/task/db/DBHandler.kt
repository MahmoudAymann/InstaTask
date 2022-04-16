package com.instabug.task.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.instabug.task.ui.adapter.ItemWord

class DBHandler private constructor(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query: String = buildString {
            append("CREATE TABLE ").append(TABLE_NAME)
            append(" (")
            append(WordsEntry.ID_COL).append(" INTEGER PRIMARY KEY,")
            append(WordsEntry.WORD_COL).append(" TEXT,")
            append(WordsEntry.COUNT_COL).append(" INTEGER")
            append(")")
        }

        db.execSQL(query)
    }

    // this method is use to add new course to our sqlite database.
    private fun addNewWord(item: ItemWord) {
        val db: SQLiteDatabase? = this.writableDatabase

        val values = ContentValues().apply {
            put(WordsEntry.ID_COL, item.id)
            put(WordsEntry.WORD_COL, item.word)
            put(WordsEntry.COUNT_COL, item.count)
        }

        db?.insert(TABLE_NAME, null, values)
        db?.close()
    }

    fun insertWords(list: List<ItemWord>): Boolean {
        list.forEach {
            addNewWord(it)
        }
        return true //marker for finish
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun getWords(): List<ItemWord> {
        val listOfWords = mutableListOf<ItemWord>()
        val db: SQLiteDatabase? = this.readableDatabase
        try {
            val c: Cursor? = db?.query(
                TABLE_NAME, arrayOf(
                    WordsEntry.ID_COL, WordsEntry.WORD_COL, WordsEntry.COUNT_COL
                ), null, null, null, null, null
            )
            val numRows: Int = c?.count ?: 0
            c?.moveToFirst()
            for (i in 0 until numRows) {
                listOfWords.add(
                    ItemWord(
                        id = c?.getInt(0) ?: 0,
                        word = c?.getString(1).orEmpty(),
                        count = c?.getInt(2) ?: 0
                    )
                )
                c?.moveToNext()
            }
        } catch (e: SQLException) {
            Log.e("Exception on query", e.toString())
        }
        return listOfWords
    }

    companion object {
        private var sInstance: DBHandler? = null
        fun getInstance(
            context: Context
        ): DBHandler {
            if (sInstance == null) {
                sInstance = DBHandler(context)
            }
            return sInstance as DBHandler
        }

        private const val DB_NAME = "instadb"
        private const val TABLE_NAME = "words"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

        // creating a constant variables for our database.
        private const val DB_VERSION = 1

    }
}