package com.android.hanple.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
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

    private lateinit var callback : OnBackPressedCallback
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val searchFragment = SearchFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchFragment)
                transaction.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchTimeFragment, callback)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){

        binding.tvSearchTimeSkip.setOnClickListener {
            val searchTransportationFragment = SearchTransportationFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchTransportationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.tvSearchTimeNext.setOnClickListener {
            val fromStart: String?
            val toStart: String?
            fromStart = binding.edSearchTimeFrom.text.toString()
            toStart = binding.edSearchTimeTo.text.toString()
            if (fromStart != "" && toStart != "") {
                viewModel.getTimeStamp(fromStart, toStart)
            }

            val searchTransportationFragment = SearchTransportationFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchTransportationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.edSearchTimeFrom.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                activity?.let { hideKeyBoard(it) }

                return@OnKeyListener true
            }
            false
        })

        binding.edSearchTimeTo.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val searchTransportationFragment = SearchTransportationFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchTransportationFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                activity?.let { hideKeyBoard(it) }

                return@OnKeyListener true
            }
            false
        })
        }
    private fun hideKeyBoard(activity: Activity) {
        val keyBoard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
    private fun putViewModelData(){
        viewModel.getDustData()
        viewModel.getWeatherData()
    }

}