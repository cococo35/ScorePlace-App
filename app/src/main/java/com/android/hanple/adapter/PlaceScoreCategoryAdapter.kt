package com.android.hanple.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewRecommendItemBinding
import com.android.hanple.databinding.RecyclerviewScoreCategoryItemBinding
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest

interface OnDataClick {
    fun onItemClick(data: Place)
}
class PlaceScoreCategoryAdapter(
    private val onDataClick: OnDataClick
) : ListAdapter<com.google.android.libraries.places.api.model.Place, PlaceScoreCategoryAdapter.PlaceViewHolder>(diffCallback) {

    inner class PlaceViewHolder(
        private val binding: RecyclerviewScoreCategoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: com.google.android.libraries.places.api.model.Place) = with(binding) {
            binding.tvItemAddress.text = place.name
            binding.tvItemGrade.text = place.rating?.toString()
            binding.tvItemTime.text = place.openingHours?.hoursType?.name
//            Glide.with(itemView).load().into(binding.ivItemThumbnail)
            binding.root.setOnClickListener {
                onDataClick.onItemClick(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = RecyclerviewScoreCategoryItemBinding.inflate(
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
        val diffCallback = object : DiffUtil.ItemCallback<com.google.android.libraries.places.api.model.Place>() {
            override fun areItemsTheSame(
                oldItem: com.google.android.libraries.places.api.model.Place,
                newItem: com.google.android.libraries.places.api.model.Place
            ): Boolean {
               return oldItem.id == newItem.id
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: com.google.android.libraries.places.api.model.Place,
                newItem: com.google.android.libraries.places.api.model.Place
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}