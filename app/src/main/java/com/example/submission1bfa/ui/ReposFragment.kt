package com.example.submission1bfa.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1bfa.R
import com.example.submission1bfa.data.GithubRepos
import com.example.submission1bfa.databinding.FragmentReposBinding

class ReposFragment : Fragment() {

    private lateinit var reposBinding: FragmentReposBinding
    private lateinit var viewModel: ReposViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReposViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        reposBinding = FragmentReposBinding.inflate(inflater, container, false)
        return reposBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = arguments?.getString(ARG_LOGIN)

        reposBinding.rvRepos.setHasFixedSize(true)
        viewModel.getListRepository(login.toString())
        viewModel.reposUsers.observe(viewLifecycleOwner){ reposUser ->
            generateRecyclerView(reposUser)
            Log.d("Data ReposUser", reposUser.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading->
            showLoading(isLoading)
        }
    }

    private fun generateRecyclerView(listItems: ArrayList<GithubRepos>) {
        reposBinding.rvRepos.visibility = View.VISIBLE

        reposBinding.rvRepos.layoutManager = LinearLayoutManager(this@ReposFragment.context)
        val listReposAdapter = GithubReposAdapter(listItems)
        reposBinding.rvRepos.adapter = listReposAdapter

        listReposAdapter.setOnItemClickCallback(object : GithubReposAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubRepos) {
                Toast.makeText(this@ReposFragment.context, "${data.name.toString()} is Clicked", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            reposBinding.progressBar.visibility = View.VISIBLE
        } else {
            reposBinding.progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val ARG_LOGIN = "login_name"
    }
}