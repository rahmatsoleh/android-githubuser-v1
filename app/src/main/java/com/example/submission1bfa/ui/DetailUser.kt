package com.example.submission1bfa.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.submission1bfa.R
import com.example.submission1bfa.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity(), View.OnClickListener {
    lateinit var detailUserBinding: ActivityDetailUserBinding

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
        detailUserBinding.tvNameProfile.text = username
        detailUserBinding.btnBack.text = username


        detailUserBinding.btnBack.setOnClickListener(this)
        detailUserBinding.btnShare.setOnClickListener(this)
        detailUserBinding.btnOpenUser.setOnClickListener(this)

//        ViewPager2
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = detailUserBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailUserBinding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

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
}