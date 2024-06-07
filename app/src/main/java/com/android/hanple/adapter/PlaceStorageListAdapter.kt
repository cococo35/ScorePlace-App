package com.android.hanple.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewRecommendItemBinding
import com.android.hanple.databinding.RecyclerviewStorageItemBinding
import com.google.android.libraries.places.api.model.Place

class PlaceStorageListAdapter(
) : ListAdapter<CategoryPlace, PlaceStorageListAdapter.PlaceViewHolder>(diffCallback) {

    class PlaceViewHolder(
        private val binding: RecyclerviewStorageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: CategoryPlace) = with(binding) {
            binding.tvItemAddress.text = place.name
            binding.tvItemScore.text = place.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = RecyclerviewStorageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaceViewHolder(view)
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