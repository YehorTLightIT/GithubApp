package com.example.githubapp.api

import com.example.githubapp.model.RepositoryOwner
import com.example.githubapp.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {
    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/repositories")
    suspend fun performSearch(@Query("q") keyword: String,
                              @Query("page") page: Int,
                              @Query("sort") sort: String,
                              @Query("per_page") pageSize: Int) : Response<SearchResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String) : Response<RepositoryOwner>

    @GET("user")
    suspend fun getCurrentUser() : Response<RepositoryOwner>

}