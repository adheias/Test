package com.dicoding.picodiploma.submission2bfaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity() {

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = user.username

//        val photo: ImageView = findViewById(R.id.img_item_photo)
//        val name: TextView = findViewById(R.id.tv_name)
//        val location: TextView = findViewById(R.id.tv_location)
//        val repo: TextView = findViewById(R.id.tv_repo2)
//        val followers: TextView = findViewById(R.id.tv_followers2)
//        val following: TextView = findViewById(R.id.tv_following2)
//
////        photo.setImageResource(user.avatar)
//        name.text = user.name
//        location.text = user.location
//        repo.text = user.repository
//        followers.text = user.followers
//        following.text = user.following

    }
}