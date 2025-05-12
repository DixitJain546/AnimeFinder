package com.example.animefinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animefinder.R
import com.example.animefinder.databinding.LayoutAnimeItemBinding
import com.example.animefinder.model.AnimeData

class AnimeListAdapter(
    private val animeList: List<AnimeData>,
    private val onAnimeClick: (AnimeData) -> Unit
) : RecyclerView.Adapter<AnimeListAdapter.AnimeItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnimeItemViewHolder(
        LayoutAnimeItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = animeList.size

    override fun onBindViewHolder(holder: AnimeItemViewHolder, position: Int) {
        holder.bind(animeList[position])
    }


    inner class AnimeItemViewHolder(private val binding: LayoutAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeData) {
            with(binding) {
                root.setOnClickListener {
                    onAnimeClick(anime)
                }
                Glide.with(ivAnimePoster.context).load(anime.images?.jpg?.largeImageUrl.orEmpty())
                    .placeholder(R.drawable.ic_placeholder_anime)
                    .error(R.drawable.ic_placeholder_anime).into(ivAnimePoster)
                tvAnimeTitle.text = anime.title
                tvEpisodes.text =
                    tvEpisodes.context.getString(R.string.episodes, anime.episodes.toString())
                tvAnimeScore.text =
                    tvAnimeScore.context.getString(R.string.rating, anime.score.toString())
            }
        }
    }
}