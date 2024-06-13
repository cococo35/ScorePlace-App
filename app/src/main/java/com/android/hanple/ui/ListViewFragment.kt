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
import com.android.hanple.data.CategoryPlace
import com.android.hanple.adapter.PlaceStorageListAdapter
import com.android.hanple.databinding.FragmentListViewBinding

class ListViewFragment : Fragment() {

    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaceStorageListAdapter
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val ARG_ADDRESS = "address"
        private const val ARG_SCORE = "score"

        fun newInstance(address: String, score: Double): ListViewFragment {
            val fragment = ListViewFragment()
            val bundle = Bundle().apply {
                putString(ARG_ADDRESS, address)
                putDouble(ARG_SCORE, score)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

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

        adapter = PlaceStorageListAdapter(requireContext(), { place ->
            // 아이템 클릭 시 처리할 로직
        }, mutableListOf())

        binding.recyclerviewList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewList.adapter = adapter

        // Load places from SharedPreferences
        loadPlacesFromPreferences()

        arguments?.let {
            val address = it.getString(ARG_ADDRESS)
            val score = it.getDouble(ARG_SCORE)

            if (address != null) {
                val newPlace = CategoryPlace(address, score, null, null, null, null, null, null, true, null)
                if (adapter.currentList.none { it.address == address }) {
                    adapter.addPlace(newPlace)
                    savePlaceToPreferences(address, score)
                }
            }
        }

        binding.tvListViewMap.setOnClickListener {
            if (adapter.itemCount > 0) {
                val mapFragment = MapFragment.newInstance(adapter.currentList.toSet())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_main, mapFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.icBackbtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        adapter.onFavoriteClick = { place ->
            adapter.removePlace(place)
            savePlacesToPreferences()
        }

        adapter.onAddressClick = { place ->
            adapter.removePlace(place)
            savePlacesToPreferences()
        }
    }

    private fun savePlaceToPreferences(address: String, score: Double) {
        val editor = sharedPreferences.edit()
        editor.putString("address", address)
        editor.putFloat("score", score.toFloat())
        editor.apply()
    }

    private fun savePlacesToPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        adapter.currentList.forEachIndexed { index, place ->
            editor.putString("address_$index", place.address)
            place.score?.let { editor.putFloat("score_$index", it.toFloat()) }
        }
        editor.putInt("place_count", adapter.currentList.size)
        editor.apply()
    }

    private fun loadPlacesFromPreferences() {
        val placeCount = sharedPreferences.getInt("place_count", 0)
        val places = mutableListOf<CategoryPlace>()
        for (i in 0 until placeCount) {
            val address = sharedPreferences.getString("address_$i", null)
            val score = sharedPreferences.getFloat("score_$i", 0f)
            if (address != null) {
                places.add(CategoryPlace(address, score.toDouble(), null, null, null, null,null,null, true, null))
            }
        }
        adapter.updatePlaces(places)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
