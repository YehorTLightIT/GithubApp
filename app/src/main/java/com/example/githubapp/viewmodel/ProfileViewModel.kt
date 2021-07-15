package com.example.githubapp.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.base.BaseViewModel
import com.example.githubapp.data.UsersDataManager
import com.example.githubapp.ui.login.LoginActivity
import com.example.githubapp.ui.profile.model.ProfileUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application, private val usersDataManager: UsersDataManager) : BaseViewModel<ProfileUiModel>(app) {
    override val uiModelInternal: ProfileUiModel = ProfileUiModel()

    val onClickListener = View.OnClickListener {
        when(it.id) {
            R.id.profile_button_open_browser -> {
                navigateTo(Intent(Intent.ACTION_VIEW, Uri.parse(uiModelInternal.user.url)))
            }
            R.id.profile_button_logout -> {
                if(usersDataManager.logoutCurrentUser()){
                    navigateTo(LoginActivity::class.java, true)
                }
            }
        }
    }

    fun loadUser(){
        updateUi {
            isProgressVisible = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            usersDataManager.getLoggedInUser().onSuccess {
                updateUi {
                    isProgressVisible = false
                    user = it
                }
            }.onFailure {
                updateUser()
            }
        }
    }

    private fun updateUser(){
        viewModelScope.launch(Dispatchers.IO) {
            usersDataManager.getCurrentUser().onSuccess {
                updateUi {
                    isProgressVisible = false
                    user = it
                }
            }.onFailure {
                navigateTo(LoginActivity::class.java, true)
            }
        }
    }
}