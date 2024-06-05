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
    private val onItemClicked: (Place) -> Unit,
    private val listData: List<Place>
) : ListAdapter<Place, PlaceStorageListAdapter.PlaceViewHolder>(diffCallback) {

    class PlaceViewHolder(
        private val binding: RecyclerviewStorageItemBinding,
        private val onItemClicked: (Place) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) = with(binding) {

            // 데이터 바인딩 설정
            // tvItemAddress.text = place.address

            binding.root.setOnClickListener {
                onItemClicked(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = RecyclerviewStorageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaceViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = listData[position]
        holder.bind(currentItem)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.address == newItem.address
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}