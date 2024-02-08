package com.example.submission1bfa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1bfa.data.GithubRepos
import com.example.submission1bfa.databinding.ItemRowReposBinding

class GithubReposAdapter(private val githubRepos: ArrayList<GithubRepos>) : RecyclerView.Adapter<GithubReposAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubRepos)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    class ListViewHolder(var binding: ItemRowReposBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubReposAdapter.ListViewHolder {
        val binding = ItemRowReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubReposAdapter.ListViewHolder, position: Int) {
        val (name, description, forks, stargazers_count) = githubRepos[position]
        holder.binding.tvReposName.text = name
        holder.binding.tvStar.text = stargazers_count.toString()
        holder.binding.tvCommit.text = forks.toString()
        holder.binding.tvDescription.text = description

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(githubRepos[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = githubRepos.size

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubRepos>() {
            override fun areItemsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GithubRepos, newItem: GithubRepos): Boolean {
                return oldItem == newItem
            }
        }
    }
}