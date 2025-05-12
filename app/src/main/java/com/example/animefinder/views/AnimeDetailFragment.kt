package com.example.animefinder.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.animefinder.R
import com.example.animefinder.databinding.FragmentAnimeDetailBinding
import com.example.animefinder.model.AnimeData
import com.example.animefinder.utils.hideView
import com.example.animefinder.utils.showView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class AnimeDetailFragment : DialogFragment() {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val animeData by lazy {
        arguments?.getParcelable("animeData", AnimeData::class.java)
    }
    private var ytPlayer: YouTubePlayer? = null

    companion object {
        fun newInstance(animeData: AnimeData) = AnimeDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("animeData", animeData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeData?.let {
            with(binding) {
                lifecycle.addObserver(binding.vvTrailer)
                ivClose.setOnClickListener {
                    dismissAllowingStateLoss()
                }
                it.trailer?.url?.let { trailer ->
                    vvTrailer.showView()
                    setupYoutubePlayer(trailer)
                } ?: run {
                    vvTrailer.hideView()
                    ivPoster.showView()
                    Glide.with(ivPoster.context).load(it.images?.jpg?.largeImageUrl)
                        .placeholder(R.drawable.ic_placeholder_anime)
                        .error(R.drawable.ic_placeholder_anime).into(ivPoster)
                }
                tvAnimeTitle.text = it.title
                tvEpisodes.text =
                    tvEpisodes.context.getString(R.string.episodes, it.episodes.toString())
                tvAnimeScore.text =
                    tvAnimeScore.context.getString(R.string.rating, it.score.toString())
                tvAnimeGenre.text = tvAnimeGenre.context.getString(
                    R.string.genre, it.genres?.joinToString { genre -> genre?.name.orEmpty() })
                tvAnimeSummary.text = it.synopsis
            }
        } ?: run {
            dismissAllowingStateLoss()
        }
    }

    private fun setupYoutubePlayer(trailer: String) {
        extractYoutubeVideoId(trailer)?.let {
            binding.vvTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(it, 0f)
                    ytPlayer = youTubePlayer
                }
            })
        }?: run {
            context?.let {
                Toast.makeText(
                    it, it.getString(R.string.invalid_url), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun extractYoutubeVideoId(url: String): String? {
        val regex = Regex("(?:v=|\\/)([0-9A-Za-z_-]{11}).*")
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar)
    }

    override fun onStop() {
        super.onStop()
        ytPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ytPlayer = null
    }
}