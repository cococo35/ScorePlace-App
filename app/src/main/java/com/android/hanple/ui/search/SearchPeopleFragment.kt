package com.android.hanple.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchBinding
import com.android.hanple.databinding.FragmentSearchPeopleBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchPeopleFragment : Fragment() {


    private var _binding : FragmentSearchPeopleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private lateinit var callback : OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchPeopleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getScore()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val searchTransportationFragment = SearchTransportationFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchTransportationFragment)
                transaction.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchPeopleFragment, callback)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initView(){
        var preferType: Int = 0

        val typeList = listOf(
            binding.btnSearchPeopleButton1,
            binding.btnSearchPeopleButton2,
            binding.btnSearchPeopleButton3,
            binding.btnSearchPeopleButton4,
            binding.btnSearchPeopleButton5,
        )

        for(i in typeList.indices){
            typeList[i].apply {
                this.setOnClickListener {
                    for(j in 0..<typeList.size){
                        typeList[j].setImageResource(R.drawable.ic_check_blank_20dp)
                    }
                    this.setImageResource(R.drawable.ic_check_fill_20dp)
                    preferType = i+1
                }
            }
        }
        binding.tvSearchPeopleNext.setOnClickListener {
            viewModel.getCongestionScore(preferType)
            val searchCostFragment = SearchCostFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchCostFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    private fun getScore(){
        viewModel.getTransportScore()
    }
}