package com.example.githubapp.data

import com.example.githubapp.model.RepositoryOwner

interface UsersDataManager {
    suspend fun getRepositoryUser(username: String): Result<RepositoryOwner>
    suspend fun getCurrentUser(token: String = ""): Result<RepositoryOwner>
    suspend fun getLoggedInUser(): Result<RepositoryOwner>
    fun isUserLoggedIn(): Boolean
    fun logoutCurrentUser() : Boolean
}