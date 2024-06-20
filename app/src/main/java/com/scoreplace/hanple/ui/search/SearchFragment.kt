package com.scoreplace.hanple.ui.search

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoreplace.hanple.R
import com.scoreplace.hanple.data.CategoryPlace
import com.scoreplace.hanple.adapter.OnDataClick
import com.scoreplace.hanple.adapter.PlaceScoreCategoryAdapter
import com.scoreplace.hanple.databinding.FragmentSearchBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private var inputOk : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAutoComplete()
        (activity as MainActivity).visibleDrawerView()
        nextButtonActivated()
        initView()
        renewalData()
    }


    private fun initView(){
        binding.btnSearchNext.visibility = View.GONE
        binding.recyclerviewSearchRecommend.adapter =
            com.scoreplace.hanple.adapter.PlaceScoreCategoryAdapter(object :
                com.scoreplace.hanple.adapter.OnDataClick {
                override fun onItemClick(data: CategoryPlace) {
                    clickRecommendPlace(data)
                }
            })
        binding.recyclerviewSearchRecommend.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        viewModel.recommendPlace.observe(viewLifecycleOwner){
            (binding.recyclerviewSearchRecommend.adapter as PlaceScoreCategoryAdapter).submitList(it)
        }
        binding.btnSearchNext.setOnClickListener {
            val searchTimeFragment = SearchTimeFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
            transaction.replace(R.id.fr_main, searchTimeFragment)
            transaction.commit()
        }
        viewModel.resetTimeStamp()
        viewModel.resetTime()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initAutoComplete(){
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.start_autocomplete) as? AutocompleteSupportFragment

        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PRICE_LEVEL, Place.Field.PHOTO_METADATAS))
        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.d("선택 장소", "Place: ${place.name}, ${place.id},${place.latLng}, ${place.priceLevel}")
                viewModel.getSelectPlaceLatLng(place.latLng!!)
                viewModel.getCongestionData(place.name!!)
                viewModel.getPlacedata(place)
                inputOk = true
            }
            override fun onError(status: Status) {
                Log.i(ContentValues.TAG, "An error occurred: $status")
            }
        })

    }
    private fun nextButtonActivated(){
        viewModel.selectPlace.observe(viewLifecycleOwner){
            if(it?.id != null){
                binding.btnSearchNext.visibility = View.VISIBLE
            }
            else {
                binding.btnSearchNext.visibility = View.GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renewalData(){
        lifecycleScope.launch {
            whenStarted {
                while(true) {
                    delay(1000)
                    (binding.recyclerviewSearchRecommend.adapter as com.scoreplace.hanple.adapter.PlaceScoreCategoryAdapter).notifyDataSetChanged()
                }
            }
        }
    }

    private fun clickRecommendPlace(place: CategoryPlace){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("${place.name}의 추천 점수를 알아볼까요?")
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                val searchTimeFragment = SearchTimeFragment()
                viewModel.getSelectRecommendData(place)
                viewModel.getSelectPlaceLatLng(place.latLng!!)
                viewModel.getCongestionData(place.name!!)
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                transaction.replace(R.id.fr_main, searchTimeFragment)
                transaction.commit()
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
            .show()
    }
}