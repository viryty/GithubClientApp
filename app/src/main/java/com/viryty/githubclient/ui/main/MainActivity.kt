package com.viryty.githubclient.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.viryty.githubclient.R
import com.viryty.githubclient.databinding.ActivityMainBinding
import com.viryty.githubclient.ui.fragments.downloads.DownloadsFragment
import com.viryty.githubclient.ui.fragments.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener {
            return@setOnItemSelectedListener when(it.itemId) {
                R.id.actionSearch -> {
                    loadFragment(SearchFragment.newInstance())
                    true
                }
                R.id.actionDownloads -> {
                    loadFragment(DownloadsFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        loadFragment(SearchFragment.newInstance())
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}