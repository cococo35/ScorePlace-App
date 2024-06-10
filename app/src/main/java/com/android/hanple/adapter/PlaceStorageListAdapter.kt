package com.android.hanple.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.databinding.RecyclerviewStorageItemBinding

class PlaceStorageListAdapter(
    private val context: Context,
    private val onItemClick: (CategoryPlace) -> Unit,
    private val places: MutableList<CategoryPlace>
) : RecyclerView.Adapter<PlaceStorageListAdapter.PlaceViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    var onFavoriteClick: ((CategoryPlace) -> Unit)? = null

    inner class PlaceViewHolder(
        private val binding: RecyclerviewStorageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: CategoryPlace) = with(binding) {
            tvItemAddress.text = place.address
            tvItemScore.text = place.score.toString()

            root.setOnClickListener {
                onItemClick(place)
            }

            tvItemAddress.setOnClickListener {
                removePlace(place)
            }

            ivItemFavorite.setOnClickListener {
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
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentItem = places[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = places.size

    fun addPlace(newPlace: CategoryPlace) {
        places.add(newPlace)
        savePreferences()
        notifyDataSetChanged()
    }

    fun removePlace(place: CategoryPlace) {
        places.remove(place)
        savePreferences()
        notifyDataSetChanged()
    }

    private fun savePreferences() {
        val editor = sharedPreferences.edit()
        val set = places.map { it.address }.toSet()
        editor.putStringSet("bookmarkedPlaces", set)
        editor.apply()
    }

    fun loadPreferences() {
        val set = sharedPreferences.getStringSet("bookmarkedPlaces", setOf()) ?: setOf()
        places.clear()
        places.addAll(set.map { CategoryPlace(it, 0.0, null, null, null, true, null) })
        notifyDataSetChanged()
    }

    // currentList 속성 추가
    val currentList: List<CategoryPlace>
        get() = places.toList()
}
