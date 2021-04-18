package com.dicoding.picodiploma.submission2bfaa.db

import android.provider.BaseColumns

class UserContract {


    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favourite_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val AVATAR = "avatar"

        }

    }
}