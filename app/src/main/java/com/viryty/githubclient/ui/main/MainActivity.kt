package com.viryty.githubclient.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.viryty.githubclient.R
import com.viryty.githubclient.databinding.ActivityMainBinding
import com.viryty.githubclient.ui.CurrentFragment
import com.viryty.githubclient.ui.fragments.downloads.DownloadsFragment
import com.viryty.githubclient.ui.fragments.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener {
            return@setOnItemSelectedListener when(it.itemId) {
                R.id.actionSearch -> loadFragment(SearchFragment.newInstance(), CurrentFragment.SEARCH)
                R.id.actionDownloads -> loadFragment(DownloadsFragment.newInstance(), CurrentFragment.DOWNLOAD)
                else -> false
            }
        }

        when(viewModel.getCurrentFragment()) {
            CurrentFragment.SEARCH -> loadFragment(SearchFragment.newInstance(), CurrentFragment.SEARCH)
            CurrentFragment.DOWNLOAD -> loadFragment(DownloadsFragment.newInstance(), CurrentFragment.DOWNLOAD)
        }
    }

    private fun loadFragment(fragment: Fragment, currentFragment: CurrentFragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        viewModel.setCurrentFragment(currentFragment)
        return true
    }
}