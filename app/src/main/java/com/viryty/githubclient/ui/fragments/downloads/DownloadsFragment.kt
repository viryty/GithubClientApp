package com.viryty.githubclient.ui.fragments.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.viryty.githubclient.adapter.DownloadsAdapter
import com.viryty.githubclient.data.repos.room.Download
import com.viryty.githubclient.databinding.FragmentDownloadsBinding
import com.viryty.githubclient.ui.main.MainViewModel

class DownloadsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentDownloadsBinding

    private var adapter: DownloadsAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = DownloadsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DownloadsAdapter(listOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadsBinding.inflate(inflater, container, false)

        binding.downloadsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.downloadsRecyclerView.adapter = adapter

        viewModel.downloads.observe(viewLifecycleOwner) { downloads ->
            downloads?.let {
                if(it.isNotEmpty()) successLoad(downloads)
                else emptyDataBase()
            }
        }

        return binding.root
    }

    private fun successLoad(downloads: List<Download>) {
        adapter?.updateList(downloads)
        binding.progressBar.visibility = View.GONE
        binding.downloadsRecyclerView.visibility = View.VISIBLE
    }

    private fun emptyDataBase() {
        binding.progressBar.visibility = View.GONE
        binding.downloadsRecyclerView.visibility = View.GONE
        binding.infoMessage.visibility = View.VISIBLE
    }
}