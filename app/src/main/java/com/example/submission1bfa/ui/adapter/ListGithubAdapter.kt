package com.example.submission1bfa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1bfa.data.GithubUsers
import com.example.submission1bfa.databinding.ItemRowGithubBinding

class ListGithubAdapter(private val listGithub: ArrayList<GithubUsers>) :
    RecyclerView.Adapter<ListGithubAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUsers)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listGithub[holder.adapterPosition]) }
    }

}