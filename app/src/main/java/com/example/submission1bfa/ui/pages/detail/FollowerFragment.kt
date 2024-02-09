package com.example.submission1bfa.ui.pages.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.databinding.FragmentFollowerBinding
import com.example.submission1bfa.ui.adapter.ListGithubAdapter

class FollowerFragment : Fragment() {
    private lateinit var followerBinding: FragmentFollowerBinding
    private lateinit var viewModel: FollowerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        followerBinding = FragmentFollowerBinding.inflate(inflater,container, false)
        return followerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = arguments?.getString(ARG_LOGIN)

        followerBinding.rvRepos.setHasFixedSize(true)
        viewModel.getFollowerUsers(login.toString())

        viewModel.followerUser.observe(viewLifecycleOwner) { followerUser ->
            generateAdapterFollower(followerUser)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { it ->
            showLoading(it)
        }

    }

    fun generateAdapterFollower(user: ArrayList<GithubUsers>) {
        followerBinding.rvRepos.visibility = View.VISIBLE

        followerBinding.rvRepos.layoutManager = LinearLayoutManager(this@FollowerFragment.context)
        val listFollowerAdapter = ListGithubAdapter(user)
        followerBinding.rvRepos.adapter = listFollowerAdapter

        listFollowerAdapter.setOnItemClickCallback(object : ListGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUsers) {
                val detailIntent = Intent(this@FollowerFragment.context, DetailUser::class.java)
                detailIntent.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                startActivity(detailIntent)
            }
        })
    }

    fun showLoading(load: Boolean) {
        if (load) {
            followerBinding.progressBar.visibility = View.VISIBLE
        } else {
            followerBinding.progressBar.visibility = View.GONE
        }
    }


    companion object {
        const val ARG_LOGIN = "login_name"
        private const val TAG = "FollowerFragment"
    }
}