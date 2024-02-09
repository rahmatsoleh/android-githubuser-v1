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
import com.example.submission1bfa.databinding.FragmentFollowingBinding
import com.example.submission1bfa.ui.adapter.ListGithubAdapter

class FollowingFragment : Fragment() {
    private lateinit var followingBinding: FragmentFollowingBinding
    private lateinit var viewModel: FollowingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        followingBinding = FragmentFollowingBinding.inflate(inflater, container, false)

        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = arguments?.getString(ARG_LOGIN)

        followingBinding.rvRepos.setHasFixedSize(true)
        viewModel.getDataFollowing(login.toString())

        viewModel.followingUsers.observe(viewLifecycleOwner){ users ->
            generateListAdapter(users)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){ it->
            showLoading(it)
        }

    }

    fun generateListAdapter(user: ArrayList<GithubUsers>) {
        followingBinding.rvRepos.visibility = View.VISIBLE

        val listFollowingAdapter = ListGithubAdapter(user)
        followingBinding.rvRepos.layoutManager = LinearLayoutManager(this@FollowingFragment.context)
        followingBinding.rvRepos.adapter = listFollowingAdapter

        listFollowingAdapter.setOnItemClickCallback(object: ListGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubUsers) {
                val intent = Intent(this@FollowingFragment.context, DetailUser::class.java)
                intent.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })
    }

    fun showLoading(load: Boolean) {
        if (load) {
            followingBinding.progressBar.visibility = View.VISIBLE
        } else {
            followingBinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_LOGIN = "login_name"
        private const val TAG = "FollowingFragment"
    }
}