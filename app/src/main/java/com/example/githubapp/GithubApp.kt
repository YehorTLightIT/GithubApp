package com.example.githubapp

import android.app.Application
import com.example.githubapp.di.KoinDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubApp)
            modules(KoinDI.getAppModules())
        }
    }
}