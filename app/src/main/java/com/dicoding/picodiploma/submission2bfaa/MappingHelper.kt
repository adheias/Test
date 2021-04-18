package com.dicoding.picodiploma.submission2bfaa

import android.database.Cursor
import com.dicoding.picodiploma.submission2bfaa.db.UserContract

object MappingHelper {
    fun mapCursorToArray(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(UserContract.UserColumns._ID))
                val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.AVATAR))
                userList.add(User(id.toString(), username, avatar))
            }
        }
        return userList
    }
}