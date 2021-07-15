package com.example.githubapp.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.githubapp.R
import com.example.githubapp.databinding.ItemRepositoryBinding
import com.example.githubapp.model.RepositoryModel
import com.example.githubapp.model.RepositoryOwner
import com.example.githubapp.utils.parseAsDate
import com.example.githubapp.utils.toDp
import com.paginate.recycler.LoadingListItemCreator

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>(){

    private val repositories = mutableListOf<RepositoryModel>()
    private var clickListener: RepositoryClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<RepositoryModel>){
        repositories.clear()
        repositories.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<RepositoryModel>){
        val start = repositories.size
        val count = list.size
        repositories.addAll(list)
        notifyItemRangeInserted(start, count)
    }

    fun setRepositoryClickListener(listener: RepositoryClickListener){
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_repository, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    inner class ViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: RepositoryModel){
            binding.itemRepositoryName.text = item.name
            binding.itemRepositoryOwner.text = item.owner.username
            binding.itemRepositoryForks.text = item.forksCount.toString()
            binding.itemRepositoryIssues.text = item.issuesCount.toString()
            binding.itemRepositoryWatchers.text = item.watchersCount.toString()
            binding.itemRepositoryLanguage.text = if(item.language.isNullOrBlank()) {
                itemView.context.getString(R.string.repo_language_empty)
            } else item.language
            binding.itemRepositoryDate.text = item.updateDate.parseAsDate()
            Glide.with(binding.itemRepositoryAvatar)
                .load(item.owner.avatarUrl)
                .centerCrop()
                .transform(RoundedCorners((8).toDp(itemView.context)))
                .into(binding.itemRepositoryAvatar)

            ViewCompat.setTransitionName(binding.itemRepositoryAvatar, item.owner.username)

            binding.root.setOnClickListener { clickListener?.onRepositoryClick(item, binding.itemRepositoryAvatar) }
            binding.itemRepositoryAvatar.setOnClickListener { clickListener?.onAvatarClick(item.owner) }
        }
    }

    interface RepositoryClickListener {
        fun onRepositoryClick(repository: RepositoryModel, image: ImageView)
        fun onAvatarClick(owner: RepositoryOwner)
    }

    class LoadingItemCreator : LoadingListItemCreator {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return object : RecyclerView.ViewHolder(view){}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
            // nothing here
        }
    }
}