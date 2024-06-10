package com.android.hanple.ui

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

class ListViewFragment : Fragment() {

    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!
    private val favoritePlaces = mutableListOf<CategoryPlace>()
    private lateinit var adapter: PlaceStorageListAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("bookmarkRequestKey", this) { _, bundle ->
            val address = bundle.getString("address")
            val score = bundle.getDouble("score")

            if (address != null) {
                val newPlace = CategoryPlace(address, score, null, null, null, true, null)
                favoritePlaces.add(newPlace)
                adapter.notifyDataSetChanged()
            }
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

        adapter = PlaceStorageListAdapter({ place ->
            // 아이템 클릭 시 처리할 로직
        }, favoritePlaces)

        binding.recyclerviewList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewList.adapter = adapter

        arguments?.let {
            val address = it.getString(ARG_ADDRESS)
            val score = it.getDouble(ARG_SCORE)

            if (address != null) {
                val newPlace = CategoryPlace(address, score, null, null, null, true, null)
                favoritePlaces.add(newPlace)
                adapter.notifyDataSetChanged()
            }
        }

        binding.tvListViewMap.setOnClickListener {
            if (favoritePlaces.isNotEmpty()) {
                val mapFragment = MapFragment.newInstance(favoritePlaces)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_main, mapFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        adapter.onFavoriteClick = { place ->
            favoritePlaces.remove(place)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
