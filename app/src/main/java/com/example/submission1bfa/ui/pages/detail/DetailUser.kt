package com.example.submission1bfa.ui.pages.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission1bfa.R
import com.example.submission1bfa.data.response.DetailUserResponse
import com.example.submission1bfa.databinding.ActivityDetailUserBinding
import com.example.submission1bfa.ui.adapter.SectionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUser : AppCompatActivity(), View.OnClickListener {
    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private lateinit var loginkeyword: String
    private lateinit var htmlurl: String
    private lateinit var nameUser: String

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

        detailUserBinding.btnBack.setOnClickListener(this)
        detailUserBinding.btnShare.setOnClickListener(this)
        detailUserBinding.btnOpenUser.setOnClickListener(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.login = loginkeyword
        val viewPager: ViewPager2 = detailUserBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailUserBinding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailUserViewModel.getDetailGithubUser(loginkeyword)

        detailUserViewModel.detailUser.observe(this) { detailUser ->
            setDataDetailUser(detailUser)
            htmlurl = detailUser.htmlUrl.toString()
            nameUser = detailUser.name.toString()
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.status.observe(this) { status ->
            status?.let {
                detailUserViewModel.status.value = null
                Snackbar.make(
                    detailUserBinding.root,
                    "Something wrong get detail user $nameUser",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                finish()
            }

            R.id.btn_share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, htmlurl)
                intent.type = "text/plain"

                startActivity(Intent.createChooser(intent, "Share a github account $nameUser"))
            }

            R.id.btn_open_user -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlurl))
                startActivity(browserIntent)
            }
        }
    }

    private fun showLoading(load: Boolean) {
        if (load) {
            detailUserBinding.progressBar.visibility = View.VISIBLE
            detailUserBinding.profileGroup.visibility = View.INVISIBLE
            detailUserBinding.tvNameProfile.visibility = View.INVISIBLE
            detailUserBinding.tvDescription.visibility = View.INVISIBLE
            detailUserBinding.tvCompany.visibility = View.INVISIBLE
            detailUserBinding.tvLocation.visibility = View.INVISIBLE
        } else {
            detailUserBinding.progressBar.visibility = View.GONE
            detailUserBinding.profileGroup.visibility = View.VISIBLE
            detailUserBinding.tvNameProfile.visibility = View.VISIBLE
            detailUserBinding.tvDescription.visibility = View.VISIBLE
            detailUserBinding.tvCompany.visibility = View.VISIBLE
            detailUserBinding.tvLocation.visibility = View.VISIBLE
        }
    }

    private fun setDataDetailUser(user: DetailUserResponse) {
        if (user.bio == null) {
            detailUserBinding.tvDescription.visibility = View.INVISIBLE
        }

        if (user.company == null) {
            detailUserBinding.tvCompany.visibility = View.INVISIBLE
        }

        if (user.location == null) {
            detailUserBinding.tvLocation.visibility = View.INVISIBLE
        }

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
}