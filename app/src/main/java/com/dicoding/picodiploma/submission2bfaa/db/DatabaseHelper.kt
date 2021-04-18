package com.dicoding.picodiploma.submission2bfaa.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.submission2bfaa.db.UserContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithubapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "(${UserContract.UserColumns._ID} INTEGER PRIMARY KEY," +
                "${UserContract.UserColumns.USERNAME} TEXT NOT NULL," +
                "${UserContract.UserColumns.LOCATION}TEXT NOT NULL," +
                "${UserContract.UserColumns.REPOSITORY}TEXT NOT NULL," +
                "${UserContract.UserColumns.FOLLOWERS}TEXT NOT NULL," +
                "${UserContract.UserColumns.FOLLOWING}TEXT NOT NULL," +
                "${UserContract.UserColumns.AVATAR}TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}