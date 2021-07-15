package com.example.githubapp

import com.example.githubapp.api.GithubAPI
import com.example.githubapp.data.RepositoriesDataManager
import com.example.githubapp.data.RepositoriesDataManagerImpl
import com.example.githubapp.model.response.SearchResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class RepositoriesDataManagerTest {
    @Mock
    private val api: GithubAPI = Mockito.mock(GithubAPI::class.java)

    private lateinit var dataManager: RepositoriesDataManager

    @Before
    fun before(){
        dataManager = RepositoriesDataManagerImpl(api)
    }

    @Test
    fun checkSuccess(){
        val keyword = "test"
        val page = 1
        val perPage = 20
        val sort = RepositoriesDataManager.FILTER_STARS
        runBlocking {
            Mockito.`when`(api.performSearch(keyword, page, sort, perPage))
                .thenReturn(Response.success(SearchResponse(listOf())))
            assert(dataManager.querySearch(keyword, page, sort, perPage).isSuccess)
        }
    }

    @Test
    fun checkFailure(){
        val keyword = "test"
        val page = 1
        val perPage = 20
        val sort = RepositoriesDataManager.FILTER_STARS
        runBlocking {
            Mockito.`when`(api.performSearch(keyword, page, sort, perPage))
                .thenReturn(Response.error(404, ResponseBody.create(null, "")))
            assert(dataManager.querySearch(keyword, page, sort, perPage).isFailure)
        }
    }
}