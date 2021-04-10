package com.example.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ActivityUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        const val EXTRA_USER = "extra_user"
    }
    private lateinit var binding: ActivityUserBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
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

        userViewModel.getDetailUser().observe(this, {
            binding.apply {
                Glide.with(this@UserActivity)
                    .load(it.avatar)
                    .into(imgItemPhoto)
                tvRepo2.text = it.repository
                tvFollowers2.text = it.followers
                tvFollowing2.text = it.following
                tvName.text = it.name
                tvLocation.text = it.location

            }
            progressLoading(false)
        })
    }

    private fun progressLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    }
