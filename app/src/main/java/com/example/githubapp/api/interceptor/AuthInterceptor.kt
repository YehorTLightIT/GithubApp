package com.example.githubapp.api.interceptor

import com.example.githubapp.data.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        return if(token.isNotEmpty()){
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "token $token")
                .build()
            chain.proceed(request)
        } else {
            chain.proceed(chain.request())
        }
    }
}