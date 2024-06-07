package com.android.hanple.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewStorageItemBinding

class PlaceStorageListAdapter(
    private val onItemClick: (CategoryPlace) -> Unit,
    private val places: MutableList<CategoryPlace>
) : RecyclerView.Adapter<PlaceStorageListAdapter.ViewHolder>() {

    var onFavoriteClick: ((CategoryPlace) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewStorageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    inner class ViewHolder(private val binding: RecyclerviewStorageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: CategoryPlace) {
            binding.tvItemAddress.text = place.address
            binding.tvItemScore.text = place.score.toString()

            binding.root.setOnClickListener {
                onItemClick(place)
            }

            binding.ivItemFavorite.setOnClickListener {
                onFavoriteClick?.invoke(place)
            }
        }
    }
}
