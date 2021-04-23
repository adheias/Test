package com.dicoding.picodiploma.submission2bfaa

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2bfaa.adapter.ListUserAdapter
import com.dicoding.picodiploma.submission2bfaa.databinding.ActivityFavoriteUserBinding
import com.dicoding.picodiploma.submission2bfaa.db.UserContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.submission2bfaa.helper.MappingHelper
import com.dicoding.picodiploma.submission2bfaa.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUser : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: ListUserAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadUserAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.mData = list
            }
        }
    }


    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArray(cursor)
            }

            val user = deferredUser.await()
            if (user.size > 0) {
                adapter.setData(user)
                adapter.notifyDataSetChanged()
            } else {
                adapter.setData(user)
            }
        }
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val moveIntent = Intent(this@FavoriteUser, DetailUser::class.java)
        moveIntent.putExtra(DetailUser.EXTRA_USER, user)
        startActivity(moveIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.mData)
    }
}