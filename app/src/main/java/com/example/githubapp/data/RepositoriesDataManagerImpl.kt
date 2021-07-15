package com.example.githubapp.data

import com.example.githubapp.api.GithubAPI
import com.example.githubapp.model.RepositoryModel

class RepositoriesDataManagerImpl(private val api: GithubAPI) : RepositoriesDataManager {
    private val loadedRepositories = mutableListOf<RepositoryModel>()

    override suspend fun querySearch(keyword: String, page: Int, sort: String, pageSize: Int): Result<List<RepositoryModel>> {
        val response = api.performSearch(keyword, page, sort, pageSize)
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            val items = body.items
            if (page == 1) {
                loadedRepositories.clear()
            }
            loadedRepositories.addAll(items)
            Result.success(items)
        } else {
            Result.failure(Exception())
        }

    }

    override suspend fun getRepositoryById(id: Long): Result<RepositoryModel> {
        return loadedRepositories.find { it.id == id }?.let {
            Result.success(it)
        } ?: Result.failure(Exception())
    }
}