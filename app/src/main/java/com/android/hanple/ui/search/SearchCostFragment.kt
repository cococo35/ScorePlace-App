package com.android.hanple.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchCostBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchCostFragment : Fragment() {
    private var _binding : FragmentSearchCostBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchCostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){
        binding.tvSearchCostNext.setOnClickListener {
            var price : String? = null
                price = binding.edSearchCostInputCost.text.toString()
            viewModel.getCostScore(price)
            val searchLoadingFragment = SearchLoadingFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchLoadingFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}