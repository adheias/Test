package com.dicoding.picodiploma.submission2bfaa

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.submission2bfaa.databinding.ActivityDetailUserBinding
import com.dicoding.picodiploma.submission2bfaa.db.UserContract
import com.dicoding.picodiploma.submission2bfaa.db.UserHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userHelper: UserHelper
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()
        detailViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val username = user.username.toString()
        val avatar = user.avatar.toString()
        val id = user.id
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(
                    TAB_TITLES[position]
            )
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = user.username

        user.username.let {
            if (it != null) {
                detailViewModel.setDetailUser(it)
            }
        }

        detailViewModel.getDetailUser().observe(this, {
            binding.apply {
                Glide.with(this@DetailUser)
                        .load(it.avatar)
                        .into(imgItemPhoto)
                tvRepo2.text = it.repository
                tvFollowers2.text = it.followers
                tvFollowing2.text = it.following
                tvName.text = it.name
                tvLocation.text = it.location
            }
        })


        val values = ContentValues()
        values.put(UserContract.UserColumns._ID, id)
        values.put(UserContract.UserColumns.USERNAME, username)
        values.put(UserContract.UserColumns.AVATAR, avatar)

        setStatusFavorite(statusFavorite)
        binding.fabAdd.setOnClickListener {

            if (!statusFavorite) {
                userHelper.insert(values)
                Toast.makeText(this, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
            } else {
                userHelper.deleteById(user.id.toString())
                Toast.makeText(this, "Dihapus dari favorite", Toast.LENGTH_SHORT).show()
            }
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite)
        }

    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

}