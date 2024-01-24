package com.example.submission1bfa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1bfa.data.GithubUser
import com.example.submission1bfa.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

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
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    mainViewModel.getListGithubUser(searchView.text.toString())
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
    }

    private fun showRecycleList(listItems: ArrayList<GithubUser>) {
        mainBinding.rvGithub.layoutManager = LinearLayoutManager(this)
        val listGithubAdapter = ListGithubAdapter(listItems)
        mainBinding.rvGithub.adapter = listGithubAdapter

        listGithubAdapter.setOnItemClickCallback(object: ListGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: GithubUser) {
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            mainBinding.progressBar.visibility = View.VISIBLE
        } else {
            mainBinding.progressBar.visibility = View.GONE
        }
    }
}