package com.dicoding.picodiploma.submission2bfaa.db

import android.provider.BaseColumns

class UserContract {


    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatarTEXTTEXTTEXT"

        }

    }
}