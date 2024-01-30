package com.example.submission1bfa.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1bfa.R
import com.example.submission1bfa.data.GithubUser
import com.example.submission1bfa.databinding.ItemRowGithubBinding

class ListGithubAdapter(private val listGithub: ArrayList<GithubUser>) : RecyclerView.Adapter<ListGithubAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_github, parent, false)
//        return ListViewHolder(view)
        val binding = ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listGithub.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatar_url, type) = listGithub[position]
        Glide.with(holder.itemView.context)
            .load(avatar_url)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemUsername.text = login
        holder.binding.tvType.text = type

        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listGithub[holder.adapterPosition])}
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }

}