package com.android.hanple.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.data.CategoryPlace
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
        if (places.none { it.address == newPlace.address }) {
            places.add(newPlace)
            savePreferences()
            notifyDataSetChanged()
        }
    }

    fun removePlace(place: CategoryPlace) {
        places.remove(place)
        savePreferences()
        notifyDataSetChanged()
    }

    private fun savePreferences() {
        val editor = sharedPreferences.edit()
        places.forEachIndexed { index, place ->
            editor.putString("place_$index", place.address)
            place.score?.let { editor.putFloat("score_$index", it.toFloat()) }
        }
        editor.putInt("place_count", places.size)
        editor.apply()
    }

    fun loadPreferences() {
        val placeCount = sharedPreferences.getInt("place_count", 0)
        places.clear()
        for (i in 0 until placeCount) {
            val address = sharedPreferences.getString("place_$i", null)
            val score = sharedPreferences.getFloat("score_$i", 0f)
            if (address != null) {
                places.add(CategoryPlace(address, score.toDouble(), null, null, null, null, true, null))
            }
        }
        notifyDataSetChanged()
    }

    val currentList: List<CategoryPlace>
        get() = places.toList()
}
