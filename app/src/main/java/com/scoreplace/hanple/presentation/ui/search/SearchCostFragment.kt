package com.scoreplace.hanple.ui.search

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
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentSearchCostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCostFragment : Fragment() {
    private var _binding: FragmentSearchCostBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SearchViewModel::class.java]
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
        (activity as MainActivity).hideDrawerView()
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
                    transaction.setCustomAnimations(R.anim.to_left, R.anim.from_left)
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
                if (binding.edSearchCostInputCost.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.search_enter_amount), Toast.LENGTH_SHORT).show()
                }
                else {
                    val price: Int = binding.edSearchCostInputCost.text.toString().toInt() * 10000
                    viewModel.getCostScore(price)
                    val searchLoadingFragment = SearchLoadingFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fr_main, searchLoadingFragment)
                    transaction.commit()
                }
                activity?.let { hideKeyBoard(it) }

                return@OnKeyListener true
            }
            false
        })

        binding.apply {
            tvSearchCostNext.setOnClickListener {

                if (binding.edSearchCostInputCost.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.search_enter_amount), Toast.LENGTH_SHORT).show()
                } else {
                    val price: Int = binding.edSearchCostInputCost.text.toString().toInt() * 10000
                    viewModel.getCostScore(price)
                    val searchLoadingFragment = SearchLoadingFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fr_main, searchLoadingFragment)
                    transaction.commit()
                }
            }

            tvSearchCostSkip.setOnClickListener {
                val price = 0
                viewModel.getCostScore(price)
                val searchLoadingFragment = SearchLoadingFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchLoadingFragment)
                transaction.commit()
            }
        }
    }

    private fun hideKeyBoard(activity: Activity) {
        val keyBoard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}


