package com.example.githubapp.ui.main.model

import com.example.githubapp.R
import com.example.githubapp.base.BaseUiModel
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.model.util.SafeField

class MainUiModel : BaseUiModel() {
    var isReload: SafeField<Boolean> = SafeField(false)
    var isAppend: SafeField<Boolean> = SafeField(false)
    var repositoryList: List<RepositoryModel> = listOf()

    var isProgressVisible: Boolean = false
    var isHintVisible: Boolean = false
    var isRecyclerVisible : Boolean = false
    var hintTextResId: Int = R.string.main_hint_start

    val filterId: SafeField<Int> = SafeField(1)
}