package com.example.animefinder.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animefinder.R
import com.example.animefinder.adapters.AnimeListAdapter
import com.example.animefinder.databinding.ActivityAnimeListBinding
import com.example.animefinder.model.AnimeData
import com.example.animefinder.model.AnimeListState
import com.example.animefinder.utils.hideView
import com.example.animefinder.utils.showView
import com.example.animefinder.viewmodel.AnimeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimeListBinding
    private val viewModel: AnimeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAnimeListListener()
        viewModel.callAnimeListApi()
    }

    private fun showDetailPage(animeData: AnimeData) {
        AnimeDetailFragment.newInstance(animeData)
            .show(supportFragmentManager, "AnimeDetailFragment")
    }

    private fun setupRecyclerView(animeList: List<AnimeData>) {
        with(binding.rvAnimeList) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = AnimeListAdapter(animeList, ::showDetailPage)
        }
    }

    private fun setAnimeListListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.animeListState.collect { state ->
                    when(state) {
                        is AnimeListState.Loading -> showProgressBar(true)
                        is AnimeListState.Success -> {
                            showProgressBar()
                            if(!state.animeData?.data.isNullOrEmpty()) {
                                setupRecyclerView(state.animeData?.data!!)
                            } else {
                                Toast.makeText(
                                    this@AnimeListActivity, "No data found", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is AnimeListState.Error -> {
                            showProgressBar()
                            Toast.makeText(
                                this@AnimeListActivity, state.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }

    private fun showProgressBar(show: Boolean = false) {
        with(binding.progressBar) {
            if (show) {
                showView()
                bringToFront()
            } else {
                hideView()
            }
        }
    }
}