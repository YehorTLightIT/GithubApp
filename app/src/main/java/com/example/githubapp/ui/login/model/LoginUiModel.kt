package com.example.githubapp.ui.login.model

import com.example.githubapp.base.BaseUiModel
import com.example.githubapp.model.util.SafeField

class LoginUiModel : BaseUiModel() {
    var isProgressVisible = false
    var isInputEnabled = true
    val toastResId: SafeField<Int> = SafeField(1)
}