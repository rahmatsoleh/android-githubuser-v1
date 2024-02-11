package com.example.submission1bfa.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission1bfa.ui.pages.detail.follower.FollowerFragment
import com.example.submission1bfa.ui.pages.detail.following.FollowingFragment
import com.example.submission1bfa.ui.pages.detail.repository.ReposFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var login: String = ""
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ReposFragment()
                fragment.arguments = Bundle().apply {
                    putString(ReposFragment.ARG_LOGIN, login)
                }
            }

            1 -> {
                fragment = FollowerFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowerFragment.ARG_LOGIN, login)
                }
            }

            2 -> {
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(FollowingFragment.ARG_LOGIN, login)
                }
            }
        }
        return fragment as Fragment
    }
}