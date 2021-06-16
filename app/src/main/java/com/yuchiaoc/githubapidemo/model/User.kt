package com.yuchiaoc.githubapidemo.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("text_matches") val matches: List<TextMatch>
)

object UserDiffUtils : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

