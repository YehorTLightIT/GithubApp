package com.example.githubapp.viewmodel

import android.app.Application
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.viewModelScope
import com.example.githubapp.R
import com.example.githubapp.base.BaseViewModel
import com.example.githubapp.data.RepositoriesDataManager
import com.example.githubapp.data.UsersDataManager
import com.example.githubapp.ui.login.LoginActivity
import com.example.githubapp.ui.main.model.MainUiModel
import com.example.githubapp.ui.profile.ProfileActivity
import com.example.githubapp.utils.customview.SearchView
import com.paginate.Paginate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    private val repositoriesDataManager: RepositoriesDataManager,
    private val usersDataManager: UsersDataManager
) :
    BaseViewModel<MainUiModel>(app) {
    override val uiModelInternal: MainUiModel = MainUiModel()

    private var isLoadingNow = false
    private var isLastPage = false

    private var lastPage = 0
    private var lastKeyword = ""
    private var selectedFilter = RepositoriesDataManager.FILTER_STARS

    val searchListener = object : SearchView.SearchListener {
        override fun onSearchTriggered(keyword: String) {
            if(keyword.isNotEmpty() && lastKeyword != keyword) {
                lastKeyword = keyword
                querySearch(keyword, 1, selectedFilter)
            }
        }
    }

    val paginateCallbacks = object: Paginate.Callbacks {
        override fun onLoadMore() {
            querySearch(lastKeyword, ++lastPage, selectedFilter)
        }

        override fun isLoading(): Boolean {
            return isLoadingNow
        }

        override fun hasLoadedAllItems(): Boolean {
            return isLastPage
        }
    }

    val popupItemClickListener = PopupMenu.OnMenuItemClickListener { item ->
        selectedFilter = when(item.itemId){
            R.id.filter_stars -> RepositoriesDataManager.FILTER_STARS
            R.id.filter_forks -> RepositoriesDataManager.FILTER_FORKS
            R.id.filter_updated -> RepositoriesDataManager.FILTER_UPDATED
            else -> return@OnMenuItemClickListener false
        }
        updateUi {
            filterId.set(item.itemId)
            isRecyclerVisible = false
            isProgressVisible = true
            isHintVisible = false
        }
        querySearch(lastKeyword, 1, selectedFilter)
        true
    }

    val onClickListener = View.OnClickListener {
        if(usersDataManager.isUserLoggedIn()) {
            navigateTo(ProfileActivity::class.java)
        } else {
            navigateTo(LoginActivity::class.java)
        }
    }

    private fun querySearch(keyword: String, page: Int = 1, sort: String = RepositoriesDataManager.FILTER_STARS) {
        isLoadingNow = true
        lastPage = page
        updateUi {
            isRecyclerVisible = page != 1
            isProgressVisible = page == 1
            isHintVisible = false
        }
        viewModelScope.launch(Dispatchers.IO) {
            repositoriesDataManager.querySearch(keyword, page, sort, PER_PAGE_SIZE).onSuccess { loadedList ->
                if(loadedList.size < PER_PAGE_SIZE){
                    isLastPage = true
                }
                updateUi {
                    if (page == 1) {
                        isReload.set(true)
                    } else {
                        isAppend.set(true)
                    }
                    repositoryList = loadedList
                    isRecyclerVisible = true
                    isProgressVisible = false
                    isHintVisible = loadedList.isEmpty() && page == 1
                    hintTextResId = R.string.main_hint_no_results

                    isLoadingNow = false
                }
            }.onFailure {
                updateUi {
                    isRecyclerVisible = false
                    isProgressVisible = false
                    isHintVisible = true
                    hintTextResId = R.string.main_hint_error

                    isLastPage = true
                    isLoadingNow = false
                }
            }
        }
    }

    companion object {
        private const val PER_PAGE_SIZE = 20;
    }
}