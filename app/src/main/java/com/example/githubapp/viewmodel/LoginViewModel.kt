package com.example.githubapp.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.base.BaseViewModel
import com.example.githubapp.data.UsersDataManager
import com.example.githubapp.ui.login.model.LoginUiModel
import com.example.githubapp.ui.profile.ProfileActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(app: Application, private val usersDataManager: UsersDataManager) : BaseViewModel<LoginUiModel>(app) {
    override val uiModelInternal: LoginUiModel = LoginUiModel()

    val onClickListener = View.OnClickListener {
        when(it.id){
            R.id.login_label_token -> {
                navigateTo(Intent(Intent.ACTION_VIEW, Uri.parse(URL_GET_TOKEN)))
            }
        }
    }

    fun handleSubmitClick(token: String){
        updateUi {
            isInputEnabled = false
            isProgressVisible = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            usersDataManager.getCurrentUser(token).onSuccess {
                // redirect to profile page
                navigateTo(ProfileActivity::class.java, true)
            }.onFailure {
                updateUi {
                    isInputEnabled = true
                    isProgressVisible = false
                    toastResId.set(R.string.toast_login_error)
                }
            }
        }
    }

    companion object {
        private const val URL_GET_TOKEN = "https://github.com/settings/tokens"
    }
}