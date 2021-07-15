package com.example.githubapp.model.response

import com.example.githubapp.model.RepositoryModel
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val items: List<RepositoryModel>
)