package com.example.githubapp.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
    @SerializedName("login") val username: String = "",
    @SerializedName("id") val id: Long = 0,
    @SerializedName("html_url") val url: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("blog") val blog: String = "",
    @SerializedName("public_repos") val reposCount: Int = 0,
    @SerializedName("followers") val followersCount: Int = 0,
    @SerializedName("following") val followingCount: Int = 0,
    @SerializedName("created_at") val creationDate: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("bio") val bio: String = ""
)