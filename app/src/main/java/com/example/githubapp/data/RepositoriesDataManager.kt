package com.example.githubapp.data

import com.example.githubapp.model.RepositoryModel

interface RepositoriesDataManager {
    suspend fun querySearch(keyword: String, page: Int, sort: String, pageSize: Int) : Result<List<RepositoryModel>>
    suspend fun getRepositoryById(id: Long): Result<RepositoryModel>

    companion object {
        const val FILTER_STARS = "stars"
        const val FILTER_FORKS = "forks"
        const val FILTER_UPDATED = "updated"
    }
}