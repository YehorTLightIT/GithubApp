package com.example.githubapp.data

interface TokenManager {
    fun saveToken(token: String)
    fun getToken(): String
}