package com.dicoding.picodiploma.submission2bfaa.db

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHORITY = "com.dicoding.picodiploma.submission2bfaa"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatarTEXTTEXTTEXT"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}