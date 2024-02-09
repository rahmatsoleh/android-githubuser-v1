package com.example.submission1bfa.ui.pages.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.submission1bfa.ui.pages.detail.DetailUser
import com.example.submission1bfa.ui.adapter.ListGithubAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var keyword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        with(mainBinding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getListGithubUser(searchView.text.toString())
                    keyword = searchView.text.toString()
                    false
                }
        }

        mainBinding.rvGithub.setHasFixedSize(true)
        mainViewModel.githubUsers.observe(this) { githubUsers->
            showRecycleList(githubUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.totalCount.observe(this) {
            setNumFoundHeader(it)
        }

        mainViewModel.status.observe(this, Observer { status ->
            status?.let {
                mainViewModel.status.value = null
                Snackbar.make(mainBinding.root, "Something wrong response data", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRecycleList(listItems: ArrayList<GithubUsers>) {
        mainBinding.rvGithub.visibility = View.VISIBLE
        mainBinding.tvFirstStatus.visibility = View.INVISIBLE

        mainBinding.rvGithub.layoutManager = LinearLayoutManager(this)
        val listGithubAdapter = ListGithubAdapter(listItems)
        mainBinding.rvGithub.adapter = listGithubAdapter

        listGithubAdapter.setOnItemClickCallback(object: ListGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUsers) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: GithubUsers) {
        val detailIntent = Intent(this@MainActivity, DetailUser::class.java)
        detailIntent.putExtra(DetailUser.EXTRA_USERNAME, user.login)
        startActivity(detailIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            mainBinding.ivGithub.visibility = View.INVISIBLE
            mainBinding.tvFirstStatus.visibility = View.INVISIBLE
            mainBinding.llNotfound.visibility = View.INVISIBLE
            mainBinding.progressBar.visibility = View.VISIBLE
        } else {
            mainBinding.progressBar.visibility = View.GONE
        }
    }

    private fun setNumFoundHeader(count: Int) {
        mainBinding.tvFirstTextHeader.visibility = View.INVISIBLE
        mainBinding.tvNumFound.visibility = View.VISIBLE
        mainBinding.tvFoundDesc.visibility = View.VISIBLE

        mainBinding.tvNumFound.text = count.toString()

        if (count < 1) {
            mainBinding.rvGithub.visibility = View.INVISIBLE

            mainBinding.ivGithub.visibility = View.VISIBLE
            mainBinding.llNotfound.visibility = View.VISIBLE
            mainBinding.tvNotFound.text = keyword
        } else {
            mainBinding.rvGithub.visibility = View.VISIBLE

            mainBinding.ivGithub.visibility = View.INVISIBLE
            mainBinding.llNotfound.visibility = View.INVISIBLE
        }
    }
}