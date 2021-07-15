package com.example.githubapp.ui.details

import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.githubapp.R
import com.example.githubapp.base.BaseActivity
import com.example.githubapp.databinding.ActivityDetailsBinding
import com.example.githubapp.ui.details.model.DetailsUiModel
import com.example.githubapp.utils.ifTrue
import com.example.githubapp.utils.parseAsDate
import com.example.githubapp.utils.toDp
import com.example.githubapp.viewmodel.DetailsViewModel

class DetailsActivity : BaseActivity<DetailsViewModel, ActivityDetailsBinding, DetailsUiModel>(){
    override fun getLayoutId(): Int = R.layout.activity_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleTransition()
        viewModel.handleIntent(intent)
    }

    override fun onUiUpdates(model: DetailsUiModel) {
        binding.detailsProgress.isVisible = model.isProgressVisible

        with(model.user){
            binding.detailsUsername.text = username
            binding.detailsUserCreationDate.text = creationDate.parseAsDate()
            binding.detailsLocation.text = location
            binding.detailsReposCount.text = reposCount.toString()
        }

        with(model.repository){
            binding.detailsLanguage.text = if(language.isNullOrBlank()) {
                getString(R.string.repo_language_empty)
            } else language
            binding.detailsBranch.text = defaultBranch
            binding.detailsCreationDate.text = creationDate.parseAsDate()
            binding.detailsUpdatedDate.text = updateDate.parseAsDate()
            binding.detailsRepoName.text = name
            binding.detailsRepoDescription.text = description
            binding.detailsRepositoryForks.text = forksCount.toString()
            binding.detailsRepositoryIssues.text = issuesCount.toString()
            binding.detailsRepositoryWatchers.text = watchersCount.toString()
            binding.detailsSize.text = getString(R.string.placeholder_details_kb, size)
        }

        binding.detailsButtonOpenInBrowser.setOnClickListener(viewModel.onClickListener)
        binding.detailsAvatar.setOnClickListener(viewModel.onClickListener)

        model.onErrorBack.ifTrue {
            finish()
        }
    }

    private fun handleTransition(){
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val transitionName = intent.getStringExtra(KEY_TRANSITION_NAME)
        binding.detailsAvatar.transitionName = transitionName
        Glide.with(this)
            .load(avatarUrl)
            .centerCrop()
            .transform(RoundedCorners((8).toDp(this)))
            .into(binding.detailsAvatar)
    }

    override fun finish() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.finish()
    }

    companion object {
        const val KEY_REPOSITORY_ID = "repository_id"
        const val KEY_TRANSITION_NAME = "transition_name"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }
}