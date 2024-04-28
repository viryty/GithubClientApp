package com.viryty.githubclient.ui.fragments.search

import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.viryty.githubclient.R
import com.viryty.githubclient.adapter.ReposAdapter
import com.viryty.githubclient.data.repos.remote.Repo
import com.viryty.githubclient.data.repos.remote.Status
import com.viryty.githubclient.databinding.FragmentSearchBinding
import com.viryty.githubclient.listener.DownloadRepoListener
import com.viryty.githubclient.listener.OpenWebListener
import com.viryty.githubclient.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), OpenWebListener, DownloadRepoListener {

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding: FragmentSearchBinding

    private var adapter: ReposAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ReposAdapter(listOf(), this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.reposRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reposRecyclerView.adapter = adapter
        binding.errorMessage.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Error", binding.errorMessage.text.toString())
            clipboard.setPrimaryClip(clip)
        }

        when (viewModel.reposState.value.status) {
            Status.SUCCESS -> successSearch(viewModel.reposState.value.data!!)
            Status.ERROR -> errorSearch(viewModel.reposState.value.message.toString())
            else -> {}
        }

        binding.searchBtn.setOnClickListener {
            if(!binding.usernameET.text.isNullOrEmpty()) {
                loadingSearch()
                viewModel.getRepos(binding.usernameET.text.toString())
                lifecycleScope.launch {
                    viewModel.reposState.collect {
                        when (it.status) {
                            Status.LOADING -> loadingSearch()
                            Status.SUCCESS -> successSearch(it.data!!)
                            Status.ERROR -> errorSearch(it.message.toString())
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun loadingSearch() {
        binding.infoMessage.visibility = View.GONE
        binding.reposRecyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun errorSearch(message: String) {
        binding.infoMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.reposRecyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.errorMessage.text = message
    }

    private fun successSearch(list: List<Repo>) {
        binding.infoMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.reposRecyclerView.visibility = View.VISIBLE
        adapter?.updateList(list)
    }

    override fun onOpenWeb(url: String) {
        val packageName = "com.android.chrome"

        val colorParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.primary))
            .build()

        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setInstantAppsEnabled(true)
            .setDefaultColorSchemeParams(colorParams)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .build()

        tabsIntent.intent.setPackage(packageName)

        try {
            tabsIntent.launchUrl(requireContext(), Uri.parse(url))
        } catch (e: Exception) {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

    }

    override fun onDownloadRepo(user: String, repo: String) {
        val url = "https://api.github.com/repos/$user/$repo/zipball/main"
        val name = "$repo.zip"

        val dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(repo)
            .setDescription("$name/$repo")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        dm.enqueue(request)
    }
}