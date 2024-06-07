package com.android.hanple.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchBinding
import com.android.hanple.databinding.FragmentSearchTimeBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchTimeFragment : Fragment() {
    private var _binding : FragmentSearchTimeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTimeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        putViewModelData()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun initView(){
        val fromStart : String?
        val toStart: String?
        fromStart = binding.edSearchTimeFrom.text.toString()
        toStart = binding.edSearchTimeTo.text.toString()
        if(fromStart != null && toStart != null){
            viewModel.getTimeStamp(fromStart, toStart)
        }
        binding.tvSearchTimeSkip.setOnClickListener {
            val searchTransportationFragment = SearchTransportationFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchTransportationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.tvSearchTimeNext.setOnClickListener {
            val searchTransportationFragment = SearchTransportationFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchTransportationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    private fun putViewModelData(){
        viewModel.getDustData()
        viewModel.getWeatherData()
    }

}