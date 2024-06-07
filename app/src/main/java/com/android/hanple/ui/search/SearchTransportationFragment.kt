package com.android.hanple.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchTimeBinding
import com.android.hanple.databinding.FragmentSearchTransportationBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchTransportationFragment : Fragment() {
    private var _binding : FragmentSearchTransportationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTransportationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getScore()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initView(){
        binding.cdSearchTransportationCar.setOnClickListener {
            viewModel.getParkingData()
        }
        binding.cdSearchTransportationPublic.setOnClickListener {
            viewModel.usePublic()
        }
        binding.tvSearchTransportationNext.setOnClickListener {
            val searchPeopleFragment = SearchPeopleFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchPeopleFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    private fun getScore(){
        viewModel.getDustScore()
        viewModel.getWeatherScore()
    }
}