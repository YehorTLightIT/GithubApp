package com.example.githubapp.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.base.BaseViewModel
import com.example.githubapp.data.RepositoriesDataManager
import com.example.githubapp.data.UsersDataManager
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.ui.details.DetailsActivity
import com.example.githubapp.ui.details.model.DetailsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(app: Application,
                       private val repositoriesDataManager: RepositoriesDataManager,
                       private val usersDataManager: UsersDataManager
                       ) : BaseViewModel<DetailsUiModel>(app) {
    override val uiModelInternal: DetailsUiModel = DetailsUiModel()

    val onClickListener = View.OnClickListener { v ->
        when(v.id){
            R.id.details_avatar -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uiModelInternal.user.url))
                navigateTo(browserIntent)
            }
            R.id.details_button_open_in_browser -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uiModelInternal.repository.url))
                navigateTo(browserIntent)
            }
        }
    }

    fun handleIntent(intent: Intent){
        val repositoryId = intent.getLongExtra(DetailsActivity.KEY_REPOSITORY_ID, 0L)
        loadRepository(repositoryId)
    }

    private fun loadRepository(id: Long){
        updateUi {
            isProgressVisible = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            repositoriesDataManager.getRepositoryById(id).onSuccess { repo ->
                loadUser(repo)
            }.onFailure {
                updateUi {
                    onErrorBack.set(true)
                }
            }
        }
    }

    private fun loadUser(repository: RepositoryModel){
        viewModelScope.launch(Dispatchers.IO) {
            usersDataManager.getRepositoryUser(repository.owner.username).onSuccess { user ->
                updateUi {
                    isProgressVisible = false
                    this.repository = repository
                    this.user = user
                }
            }.onFailure {
                updateUi {
                    onErrorBack.set(true)
                }
            }
        }
    }
}