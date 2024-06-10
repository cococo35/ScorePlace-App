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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchCostBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchCostFragment : Fragment() {
    private var _binding: FragmentSearchCostBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private lateinit var callback: OnBackPressedCallback
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val mainDrawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
                if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
                    mainDrawer.closeDrawer(GravityCompat.START)
                } else {
                    val searchPeopleFragment = SearchPeopleFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fr_main, searchPeopleFragment)
                    transaction.commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchCostFragment, callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun initView(){

        binding.edSearchCostInputCost.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val price : Int = binding.edSearchCostInputCost.text.toString().toInt() * 10000
                viewModel.getCostScore(price)
                val searchLoadingFragment = SearchLoadingFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchLoadingFragment)
                transaction.addToBackStack(null)
                transaction.commit()

                activity?.let { hideKeyBoard(it) }

                return@OnKeyListener true
            }
            false
        })

        binding.apply {
            tvSearchCostNext.setOnClickListener {

                if (binding.edSearchCostInputCost.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), "금액을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val price: Int = binding.edSearchCostInputCost.text.toString().toInt() * 10000
                    viewModel.getCostScore(price)
                    val searchLoadingFragment = SearchLoadingFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fr_main, searchLoadingFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }

            tvSearchCostSkip.setOnClickListener {
//                val price = 0
//                viewModel.getCostScore(price)
                val searchLoadingFragment = SearchLoadingFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchLoadingFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun hideKeyBoard(activity: Activity) {
        val keyBoard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}


