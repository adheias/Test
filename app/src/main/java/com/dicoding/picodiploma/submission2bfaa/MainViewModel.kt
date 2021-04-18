package com.dicoding.picodiploma.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
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
        val url = "https://api.github.com/search/users?q=$users"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObjects = JSONObject(result)
                    val items = responseObjects.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val gson = Gson()
                        val user =
                            gson.fromJson(items.getJSONObject(i).toString(), User::class.java)
                        listItem.add(user)
                    }
                    listUser.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("onFailure: ", error?.message.toString())
            }

        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}