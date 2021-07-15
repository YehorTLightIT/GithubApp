package com.example.githubapp.ui.main.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.example.githubapp.R
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.model.RepositoryOwner
import com.example.githubapp.ui.main.adapter.RepositoryAdapter

class MainRepositoryClickListener(private val activity: Activity) : RepositoryAdapter.RepositoryClickListener {
    override fun onRepositoryClick(repository: RepositoryModel, image: ImageView) {
        Toast.makeText(activity, R.string.toast_go_pro, Toast.LENGTH_LONG).show()
    }

    override fun onAvatarClick(owner: RepositoryOwner) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(owner.url)))
    }
}