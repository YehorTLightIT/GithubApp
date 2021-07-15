package com.example.githubapp.ui.details.model

import com.example.githubapp.base.BaseUiModel
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.model.RepositoryOwner
import com.example.githubapp.model.util.SafeField

class DetailsUiModel : BaseUiModel() {
    var repository: RepositoryModel = RepositoryModel()
    var user: RepositoryOwner = RepositoryOwner()
    var isProgressVisible = true

    val onErrorBack: SafeField<Boolean> = SafeField(false)
}