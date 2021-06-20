package com.yuchiaoc.githubapidemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuchiaoc.githubapidemo.R
import com.yuchiaoc.githubapidemo.model.User
import com.yuchiaoc.githubapidemo.model.UserDiffUtils

class UserListAdapter : PagedListAdapter<User, RecyclerView.ViewHolder>(UserDiffUtils) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as ViewHolder).bind(it) }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        Glide.with(itemView).load(user.avatar).into(itemView.findViewById(R.id.avatar))
        itemView.findViewById<TextView>(R.id.name).text = user.matches[0].name
    }
}