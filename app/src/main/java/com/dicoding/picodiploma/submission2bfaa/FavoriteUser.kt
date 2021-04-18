package com.dicoding.picodiploma.submission2bfaa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2bfaa.databinding.ActivityFavoriteUserBinding
import com.dicoding.picodiploma.submission2bfaa.db.UserHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUser : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()
        loadUserAsync()

    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val userHelper = UserHelper.getInstance(applicationContext)
            userHelper.open()
            val deferredUser = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
                MappingHelper.mapCursorToArray(cursor)
            }

            val user = deferredUser.await()
            if (user.size > 0) {
                adapter.mData = user
            } else {
                adapter.mData = ArrayList()
            }
            userHelper.close()
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
}