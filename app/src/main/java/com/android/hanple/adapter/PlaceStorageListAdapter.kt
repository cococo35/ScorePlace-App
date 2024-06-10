package com.android.hanple.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.hanple.R
import com.android.hanple.adapter.CategoryPlace
import com.android.hanple.adapter.PlaceStorageListAdapter
import com.android.hanple.databinding.FragmentListViewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.android.hanple.ui.search.ScoreFragment

class ListViewFragment : Fragment() {

    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaceStorageListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("favorite_places", Context.MODE_PRIVATE)

        adapter = PlaceStorageListAdapter({ place ->
            // 아이템 클릭 시 처리할 로직
        }, mutableListOf())

        binding.recyclerviewList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewList.adapter = adapter

        loadFavoritePlaces()

        binding.tvListViewMap.setOnClickListener {
            if (adapter.currentList.isNotEmpty()) {
                val mapFragment = MapFragment.newInstance(adapter.currentList)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_main, mapFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.icBackbtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, ScoreFragment())
                .addToBackStack(null)
                .commit()
        }

        adapter.onFavoriteClick = { place ->
            val newList = adapter.currentList.toMutableList().apply {
                remove(place)
            }
            saveFavoritePlaces(newList)
            adapter.submitList(newList)
        }
    }

    private fun loadFavoritePlaces() {
        val favoritePlacesJson = sharedPreferences.getString("favorite_places", null)
        val favoritePlaces = if (favoritePlacesJson != null) {
            gson.fromJson<List<CategoryPlace>>(favoritePlacesJson, object : TypeToken<List<CategoryPlace>>() {}.type)
        } else {
            emptyList()
        }
        adapter.submitList(favoritePlaces)
    }

    private fun saveFavoritePlaces(places: List<CategoryPlace>) {
        sharedPreferences.edit().putString("favorite_places", gson.toJson(places)).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
