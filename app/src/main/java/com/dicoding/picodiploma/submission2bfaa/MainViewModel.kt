package com.dicoding.picodiploma.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(users: String) {
        val listItem = ArrayList<User>()
        val apiKey = "token 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"
        val url = "https://api.github.com/search/users?q=${users}"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", apiKey)
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseString: ByteArray) {
                val result = String(responseString)
                Log.d(TAG, result)
                try {
                    val responseObjects = JSONObject(result)
                    val items = responseObjects.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = User( )
                        user.username = username
                        user.avatar = avatar
                        listItem.add(user)
                    }
                    listUser.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}