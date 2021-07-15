package com.example.githubapp.ui.login

import android.os.Bundle
import android.widget.Toast
import com.example.githubapp.R
import com.example.githubapp.base.BaseActivity
import com.example.githubapp.databinding.ActivityLoginBinding
import com.example.githubapp.ui.login.model.LoginUiModel
import com.example.githubapp.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding, LoginUiModel>(){
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginButtonSubmit.setOnClickListener {
            val token = binding.loginInputToken.text.toString()
            viewModel.handleSubmitClick(token)
        }
        binding.loginLabelToken.setOnClickListener(viewModel.onClickListener)
    }

    override fun onUiUpdates(model: LoginUiModel) {
        binding.loginButtonSubmit.isEnabled = model.isInputEnabled
        binding.loginInputToken.isEnabled = model.isInputEnabled

        model.toastResId.get { id ->
            Toast.makeText(this, id, Toast.LENGTH_LONG).show()
        }
    }
}