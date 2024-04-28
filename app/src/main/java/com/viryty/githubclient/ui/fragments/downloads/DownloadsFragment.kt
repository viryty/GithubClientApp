package com.viryty.githubclient.ui.fragments.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.viryty.githubclient.databinding.FragmentDownloadsBinding

class DownloadsFragment : Fragment() {

    private lateinit var binding: FragmentDownloadsBinding

    companion object {
        @JvmStatic
        fun newInstance() = DownloadsFragment()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadsBinding.inflate(inflater, container, false)

        binding.downloadsRecyclerView.layoutManager = LinearLayoutManager(context)
//        binding.dowloadsRecyclerView.adapter




        return binding.root
    }
}