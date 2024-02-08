package com.example.submission1bfa.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission1bfa.R
import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity(), View.OnClickListener {
    lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetaiUserViewModel>()
    private lateinit var loginkeyword: String

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.repos,
            R.string.follower,
            R.string.following,
        )
        const val EXTRA_USERNAME = "extra_username"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        loginkeyword = username.toString()
//        detailUserBinding.tvNameProfile.text = username
//        detailUserBinding.btnBack.text = username

        detailUserBinding.btnBack.setOnClickListener(this)
        detailUserBinding.btnShare.setOnClickListener(this)
        detailUserBinding.btnOpenUser.setOnClickListener(this)

//        ViewPager2
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.login = loginkeyword
        val viewPager: ViewPager2 = detailUserBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailUserBinding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailUserViewModel.getDetailGithubUser(loginkeyword)

        detailUserViewModel.detailUser.observe(this){ detailUser ->
            setDataDetailUser(detailUser)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_back -> {
                finish()
            }

            R.id.btn_share -> {
                Toast.makeText(this, "Share URL in SOsmed", Toast.LENGTH_LONG).show()
            }

            R.id.btn_open_user -> {
                Snackbar.make(detailUserBinding.root, "Something wrong response data", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDataDetailUser(user: DetailUserResponse) {
        detailUserBinding.tvRepos.text = user.publicRepos.toString()
        detailUserBinding.tvFollower.text = user.followers.toString()
        detailUserBinding.tvFollowing.text = user.following.toString()
        detailUserBinding.tvNameProfile.text = user.name.toString()
        detailUserBinding.tvDescription.text = user.bio.toString()
        detailUserBinding.tvCompany.text = user.company.toString()
        detailUserBinding.tvLocation.text = user.location.toString()
        detailUserBinding.btnBack.text = user.login.toString()

        Glide.with(this@DetailUser.applicationContext)
            .load(user.avatarUrl.toString())
            .into(detailUserBinding.ivProfile)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            detailUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailUserBinding.progressBar.visibility = View.GONE
        }
    }
}