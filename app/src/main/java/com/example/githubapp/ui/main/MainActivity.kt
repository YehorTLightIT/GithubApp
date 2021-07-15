package com.example.githubapp.ui.main

import android.os.Bundle
import android.widget.PopupMenu
import androidx.core.view.isVisible
import com.example.githubapp.R
import com.example.githubapp.base.BaseActivity
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.main.adapter.RepositoryAdapter
import com.example.githubapp.ui.main.model.MainUiModel
import com.example.githubapp.ui.main.util.MainRepositoryClickListener
import com.example.githubapp.utils.ifTrue
import com.example.githubapp.viewmodel.MainViewModel
import com.paginate.Paginate

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding, MainUiModel>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    private val adapter = RepositoryAdapter()
    private var paginate: Paginate? = null
    private var lastSelectedFilterId = R.id.filter_stars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecycler()
        binding.mainSearchView.setSearchListener(viewModel.searchListener)
        binding.mainButtonFilter.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.filter_popup_menu, popupMenu.menu)
            popupMenu.menu.findItem(lastSelectedFilterId).isVisible = false
            popupMenu.setOnMenuItemClickListener(viewModel.popupItemClickListener)
            popupMenu.show()
        }
        binding.mainButtonProfile.setOnClickListener(viewModel.onClickListener)
    }

    override fun onDestroy() {
        paginate?.unbind()
        super.onDestroy()
    }

    override fun onUiUpdates(model: MainUiModel) {
        model.isReload.ifTrue {
            paginate?.unbind()
            adapter.setData(model.repositoryList)
            paginate = Paginate.with(binding.mainRecycler, viewModel.paginateCallbacks)
                .setLoadingTriggerThreshold(2)
                .setLoadingListItemCreator(RepositoryAdapter.LoadingItemCreator())
                .build()
        }
        model.isAppend.ifTrue {
            adapter.addData(model.repositoryList)
        }

        binding.mainRecycler.isVisible = model.isRecyclerVisible
        binding.mainHint.isVisible = model.isHintVisible
        binding.mainProgress.isVisible = model.isProgressVisible
        binding.mainHint.setText(model.hintTextResId)

        model.filterId.get {
            lastSelectedFilterId = it
        }
    }

    private fun setupRecycler(){
        binding.mainRecycler.adapter = adapter
        adapter.setRepositoryClickListener(MainRepositoryClickListener(this))
    }
}