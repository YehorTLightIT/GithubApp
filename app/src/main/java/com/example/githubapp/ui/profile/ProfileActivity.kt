package com.example.githubapp.ui.profile

import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.githubapp.R
import com.example.githubapp.base.BaseActivity
import com.example.githubapp.databinding.ActivityProfileBinding
import com.example.githubapp.ui.profile.model.ProfileUiModel
import com.example.githubapp.utils.parseAsDate
import com.example.githubapp.utils.toDp
import com.example.githubapp.viewmodel.ProfileViewModel

class ProfileActivity : BaseActivity<ProfileViewModel, ActivityProfileBinding, ProfileUiModel>() {
    override fun getLayoutId(): Int = R.layout.activity_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser()
    }

    override fun onUiUpdates(model: ProfileUiModel) {
        binding.profileLoader.isVisible = model.isProgressVisible
        with(model.user){
            Glide.with(this@ProfileActivity)
                .load(avatarUrl)
                .centerCrop()
                .transform(RoundedCorners((8).toDp(this@ProfileActivity)))
                .into(binding.profileAvatar)
            binding.profileName.text = username
            binding.profileEmail.text = email
            binding.profileFollowers.text = followersCount.toString()
            binding.profileFollowing.text = followingCount.toString()
            binding.profileLocation.text = location
            binding.profileCreated.text = creationDate.parseAsDate()
            binding.profileRepos.text = reposCount.toString()
            binding.profileDescription.text = bio
        }

        binding.profileButtonOpenBrowser.setOnClickListener(viewModel.onClickListener)
        binding.profileButtonLogout.setOnClickListener(viewModel.onClickListener)
    }
}