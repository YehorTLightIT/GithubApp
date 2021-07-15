package com.example.githubapp.ui.main.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.model.RepositoryOwner
import com.example.githubapp.ui.details.DetailsActivity
import com.example.githubapp.ui.main.adapter.RepositoryAdapter

class MainRepositoryClickListener(private val activity: Activity) : RepositoryAdapter.RepositoryClickListener {
    override fun onRepositoryClick(repository: RepositoryModel, image: ImageView) {
        val transitionName = ViewCompat.getTransitionName(image)
        val intent = Intent(activity, DetailsActivity::class.java)
            .putExtra(DetailsActivity.KEY_REPOSITORY_ID, repository.id)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        if(transitionName != null) {
            intent.putExtra(DetailsActivity.KEY_TRANSITION_NAME, transitionName)
                .putExtra(DetailsActivity.EXTRA_AVATAR_URL, repository.owner.avatarUrl)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, transitionName)
            activity.startActivity(intent, options.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }

    override fun onAvatarClick(owner: RepositoryOwner) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(owner.url)))
    }
}