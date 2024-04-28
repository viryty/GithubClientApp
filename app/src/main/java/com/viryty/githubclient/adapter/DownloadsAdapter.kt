package com.viryty.githubclient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viryty.githubclient.data.repos.room.Download
import com.viryty.githubclient.databinding.ViewDownloadBinding

class DownloadsAdapter(
    private var downloads: List<Download>
): RecyclerView.Adapter<DownloadsAdapter.DownloadViewHolder>() {

    class DownloadViewHolder(
        private val binding: ViewDownloadBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(download: Download) {
            binding.name.text = download.nameRepo
            binding.owner.text = download.owner
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Download>) {
        downloads = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder =
        DownloadViewHolder(
            ViewDownloadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = downloads.size

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        holder.onBind(downloads[position])
    }
}