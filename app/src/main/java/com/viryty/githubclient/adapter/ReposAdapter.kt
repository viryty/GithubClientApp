package com.viryty.githubclient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.databinding.ViewRepoBinding
import com.viryty.githubclient.listener.DownloadRepoListener
import com.viryty.githubclient.listener.OpenWebListener

class ReposAdapter(
    private var repos: List<Repo>,
    private var openWebListener: OpenWebListener,
    private var downloadListener: DownloadRepoListener
): RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {

    class RepoViewHolder(
        private val binding: ViewRepoBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(
            repo: Repo,
            openWebListener: OpenWebListener,
            downloadListener: DownloadRepoListener
        ) {
            binding.id.text = "ID: ${repo.id}"
            binding.name.text = repo.name
            binding.ownerName.text = repo.owner.login
            Picasso.get().load(repo.owner.avatarUrl).into(binding.ownerAvatar)
            binding.openBtn.setOnClickListener {
                openWebListener.onOpenWeb(repo.htmlUrl)
            }
            binding.downloadBtn.setOnClickListener {
                downloadListener.onDownloadRepo(repo.owner.login, repo.name)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Repo>) {
        repos = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            ViewRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(repos[position], openWebListener, downloadListener)
    }
}