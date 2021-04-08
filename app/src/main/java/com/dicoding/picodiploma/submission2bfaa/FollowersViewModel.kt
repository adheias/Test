package com.dicoding.picodiploma.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class FollowersViewModel {

    companion object {
        private val TAG = FollowersViewModel::class.java.simpleName
    }

    private val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(users: String) {
        val listItemFollowers = ArrayList<User>()
        val url = "https://api.github.com/users/${users}/followers"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObjects = JSONObject(result)
                    for (i in 0 until responseObjects.length()) {
                        val gson = Gson()
                        val user = gson.fromJson(responseObjects.getJSONObject(i.toString()).toString(), User::class.java)
                        listItemFollowers.add(user)
                    }
                    listFollowers.postValue(listItemFollowers)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }
}