package com.example.githubapp.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerPaginator(
    private val infoProvider: PaginatorInfoProvider,
    private val listener: PaginatorListener
) : RecyclerView.OnScrollListener() {

    fun setupWithRecycler(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(this)
    }

    fun detachFromRecycler(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        (recyclerView.layoutManager as? LinearLayoutManager)?.also { layoutManager ->
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!infoProvider.isLoading() && !infoProvider.isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount-3
                    && firstVisibleItemPosition > 0
                    && totalItemCount >= infoProvider.getPageSize()
                ) {
                    listener.loadMore()
                }
            }
        }
    }

    interface PaginatorListener {
        fun loadMore()
    }

    interface PaginatorInfoProvider {
        fun isLoading(): Boolean
        fun isLastPage(): Boolean
        fun getPageSize(): Int
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}