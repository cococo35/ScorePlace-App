package com.android.hanple.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewStorageItemBinding

class PlaceStorageListAdapter(
    private val onItemClick: (CategoryPlace) -> Unit,
    private val places: MutableList<CategoryPlace>
) : ListAdapter<CategoryPlace, PlaceStorageListAdapter.PlaceViewHolder>(diffCallback) {

    var onFavoriteClick: ((CategoryPlace) -> Unit)? = null

    class PlaceViewHolder(
        private val binding: RecyclerviewStorageItemBinding,
        private val onItemClick: (CategoryPlace) -> Unit,
        private val onFavoriteClick: ((CategoryPlace) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: CategoryPlace) = with(binding) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = RecyclerviewStorageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaceViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CategoryPlace>() {
            override fun areItemsTheSame(oldItem: CategoryPlace, newItem: CategoryPlace): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CategoryPlace, newItem: CategoryPlace): Boolean {
                return oldItem == newItem
            }
        }
    }
}
