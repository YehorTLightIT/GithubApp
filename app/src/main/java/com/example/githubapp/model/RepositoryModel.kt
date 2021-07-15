package com.example.githubapp.model

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("owner") val owner: RepositoryOwner = RepositoryOwner(),
    @SerializedName("created_at") val creationDate: String = "",
    @SerializedName("updated_at") val updateDate: String = "",
    @SerializedName("forks") val forksCount: Int = 0,
    @SerializedName("watchers") val watchersCount: Int = 0,
    @SerializedName("open_issues") val issuesCount: Int = 0,
    @SerializedName("language") val language: String? = "Unknown",
    @SerializedName("default_branch") val defaultBranch: String = "",
    @SerializedName("size") val size: Long = 0,
    @SerializedName("html_url") val url: String = "",
    @SerializedName("description") val description: String = ""
)
