package com.example.githubapp.ui.profile.model

import com.example.githubapp.base.BaseUiModel
import com.example.githubapp.model.RepositoryOwner

class ProfileUiModel : BaseUiModel() {
    var isProgressVisible = false
    var user = RepositoryOwner()
}