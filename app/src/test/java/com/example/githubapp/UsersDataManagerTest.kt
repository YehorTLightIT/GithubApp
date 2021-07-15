package com.example.githubapp

import com.example.githubapp.api.GithubAPI
import com.example.githubapp.data.TokenManager
import com.example.githubapp.data.UsersDataManager
import com.example.githubapp.data.UsersDataManagerImpl
import com.example.githubapp.model.RepositoryOwner
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class UsersDataManagerTest {
    @Mock
    private val api: GithubAPI = Mockito.mock(GithubAPI::class.java)

    @Mock
    private val tokenManager = Mockito.mock(TokenManager::class.java)

    private lateinit var dataManager: UsersDataManager

    @Before
    fun before(){
        dataManager = UsersDataManagerImpl(api, tokenManager)
    }

    @Test
    fun checkSuccess(){
        runBlocking {
            Mockito.`when`(api.getUser(Mockito.anyString()))
                .thenReturn(Response.success(RepositoryOwner()))
            assert(dataManager.getRepositoryUser(Mockito.anyString()).isSuccess)
        }
    }

    @Test
    fun checkFailure(){
        runBlocking {
            Mockito.`when`(api.getUser(Mockito.anyString()))
                .thenReturn(Response.error(404, ResponseBody.create(null,"")))
            assert(dataManager.getRepositoryUser(Mockito.anyString()).isFailure)
        }
    }

    @Test
    fun checkSuccessLogin(){
        val token = "sample_token"
        runBlocking {
            Mockito.`when`(api.getCurrentUser())
                .thenReturn(Response.success(RepositoryOwner()))
            Mockito.`when`(tokenManager.getToken()).thenReturn(token)
            assert(dataManager.getCurrentUser(token).isSuccess)
        }
    }
}