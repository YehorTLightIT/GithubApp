package com.example.githubapp.di

import com.example.githubapp.api.GithubAPI
import com.example.githubapp.api.interceptor.AuthInterceptor
import com.example.githubapp.data.*
import com.example.githubapp.viewmodel.DetailsViewModel
import com.example.githubapp.viewmodel.LoginViewModel
import com.example.githubapp.viewmodel.MainViewModel
import com.example.githubapp.viewmodel.ProfileViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinDI {
    fun getAppModules(): List<Module>{
        return listOf(viewModelModule, dataModule, apiModule, utilModule)
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(androidApplication(), get(), get()) }
        viewModel { DetailsViewModel(androidApplication(), get(), get()) }
        viewModel { LoginViewModel(androidApplication(), get()) }
        viewModel { ProfileViewModel(androidApplication(), get()) }
    }

    private val dataModule = module {
        single<RepositoriesDataManager> { RepositoriesDataManagerImpl(get()) }
        single<UsersDataManager> { UsersDataManagerImpl(get(), get()) }
    }

    private val apiModule = module {
        single<GithubAPI> {
            Retrofit.Builder()
                .client(get())
                .baseUrl(GithubAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GithubAPI::class.java)
        }

        single<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(get<Interceptor>(StringQualifier("auth")))
                .addInterceptor(get<Interceptor>(StringQualifier("logging")))
                .build()
        }

        single<Interceptor>(StringQualifier("logging")){
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        single<Interceptor>(StringQualifier("auth")) { AuthInterceptor(get()) }
    }

    private val utilModule = module {
        single<TokenManager> { TokenManagerImpl(androidContext()) }
    }
}