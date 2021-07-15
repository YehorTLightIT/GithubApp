package com.example.githubapp.data

import com.example.githubapp.api.GithubAPI
import com.example.githubapp.model.RepositoryOwner

class UsersDataManagerImpl(private val api: GithubAPI, private val tokenManager: TokenManager) : UsersDataManager {
    private var loggedInUser: RepositoryOwner? = null

    override suspend fun getRepositoryUser(username: String): Result<RepositoryOwner> {
        val response = api.getUser(username)
        val user = response.body()
        return if(response.isSuccessful && user != null){
            Result.success(user)
        } else {
            Result.failure(Exception())
        }
    }

    override suspend fun getCurrentUser(token: String): Result<RepositoryOwner> {
        if(token.isNotEmpty()) tokenManager.saveToken(token)
        val response = api.getCurrentUser()
        val user = response.body()
        return if(response.isSuccessful && user != null){
            loggedInUser = user
            Result.success(user)
        } else {
            tokenManager.saveToken("")
            Result.failure(Exception())
        }
    }

    override suspend fun getLoggedInUser(): Result<RepositoryOwner> {
        return loggedInUser?.let {
            Result.success(it)
        } ?: Result.failure(Exception())
    }

    override fun isUserLoggedIn(): Boolean {
        return tokenManager.getToken().isNotEmpty()
    }

    override fun logoutCurrentUser(): Boolean {
        tokenManager.saveToken("")
        return true
    }
}