package com.android.hanple.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewRecommendItemBinding
import com.android.hanple.databinding.RecyclerviewScoreCategoryItemBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest

interface OnDataClick {
    fun onItemClick(data: CategoryPlace)

}
class PlaceScoreCategoryAdapter(
    private val onDataClick: OnDataClick
) : ListAdapter<CategoryPlace, PlaceScoreCategoryAdapter.PlaceViewHolder>(diffCallback) {

    inner class PlaceViewHolder(
        private val binding: RecyclerviewScoreCategoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: CategoryPlace) = with(binding) {

            binding.tvItemAddress.text = place.name
            binding.tvItemGrade.text = place.score.toString()
            binding.tvItemTime.text = place.openingHours?.hoursType?.name
            binding.root.setOnClickListener {
                onDataClick.onItemClick(place)
            }
            if(place.img != null) {
                binding.ivItemThumbnail.setImageBitmap(place.img)
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
        val diffCallback = object : DiffUtil.ItemCallback<CategoryPlace>() {
            override fun areItemsTheSame(
                oldItem: CategoryPlace,
                newItem: CategoryPlace
            ): Boolean {
               return oldItem.id == newItem.id
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: CategoryPlace,
                newItem: CategoryPlace
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}