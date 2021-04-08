package com.dicoding.picodiploma.submission2bfaa

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2bfaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()

        mainViewModel.getUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.adapter = adapter
        showLoading(false)

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val moveIntent = Intent(this@MainActivity, DetailUser::class.java)
        moveIntent.putExtra(DetailUser.EXTRA_USER, user)
        startActivity(moveIntent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataUserFromApi(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    fun getDataUserFromApi(username: String) {
        if(username.isEmpty()) return
        showLoading(true)
        mainViewModel.setUser(username)
    }

}