package com.android.hanple.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchTimeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("InflateParams")
class SearchTimeFragment : Fragment() {
    private var _binding: FragmentSearchTimeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private val timePickerBottomSheet by lazy{
        layoutInflater.inflate(R.layout.fragment_insert_time, null)
    }
    private val timePickerBottomSheetView by lazy {
        BottomSheetDialog(requireContext())
    }
    private lateinit var callback : OnBackPressedCallback
    private lateinit var localDateTime : String
    private var today : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTimeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocalTime()
        initView()
        putViewModelData()
        (activity as MainActivity).hideDrawerView()
        timePickerBottomSheetView.setContentView(timePickerBottomSheet)
        if(viewModel.readTimeStamp.value == null || viewModel.readTimeStamp.value!!.isEmpty()){
            createTimePickerBottomView()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val mainDrawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
                if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
                    mainDrawer.closeDrawer(GravityCompat.START)
                } else {
                        val searchFragment = SearchFragment()
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(R.anim.to_left, R.anim.from_left)
                        transaction.replace(R.id.fr_main, searchFragment)
                        transaction.commit()
                        viewModel.resetPlaceData()
                        viewModel.resetRecommendPlaceData()
                        (activity as MainActivity).visibleDrawerView()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchTimeFragment, callback)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun initView() {

        binding.tvSearchTimeAgainInput.setOnClickListener {
            createTimePickerBottomView()
        }

        binding.tvSearchTimeNext.setOnClickListener {

            val fromStart: String?
            val toStart: String?
            fromStart =
                binding.tvSearchTimeFromHour.text.toString() + binding.tvSearchTimeFromMinute.text.toString()
            toStart =
                binding.tvSearchTimeToHour.text.toString() + binding.tvSearchTimeToMinute.text.toString()

            if (fromStart == "" && toStart == "") {
                Toast.makeText(requireContext(), getString(R.string.search_enter_time_format), Toast.LENGTH_SHORT).show()
            } else if (
                (binding.tvSearchTimeFromHour.text.toString() + binding.tvSearchTimeFromMinute.text.toString()).toInt() >=
                (binding.tvSearchTimeToHour.text.toString() + binding.tvSearchTimeToMinute.text.toString()).toInt()
            ) {
                Toast.makeText(requireContext(), getString(R.string.search_wrong_time_format), Toast.LENGTH_SHORT).show()
            } else {
                if (
                    binding.tvSearchTimeFromHour.text.toString().toInt() in 0..23 &&
                    binding.tvSearchTimeFromMinute.text.toString().toInt() in 0..59 &&
                    binding.tvSearchTimeToHour.text.toString().toInt() in 0..23 &&
                    binding.tvSearchTimeToMinute.text.toString().toInt() in 0..59
                ) {

                    val searchTransportationFragment = SearchTransportationFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                    transaction.replace(R.id.fr_main, searchTransportationFragment)
                    transaction.commit()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.search_wrong_time_format), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.startTime.observe(viewLifecycleOwner){
            if(it == null || it == "not input"){
                binding.tvSearchTimeFromHour.text = getString(R.string._00)
                binding.tvSearchTimeFromMinute.text = getString(R.string._00)
            }
            else{
                binding.tvSearchTimeFromHour.text = it.substring(0,2)
                binding.tvSearchTimeFromMinute.text = it.substring(2)
            }
        }
        viewModel.endTime.observe(viewLifecycleOwner){
            if(it == null || it == "not input"){
                binding.tvSearchTimeToHour.text = getString(R.string._00)
                binding.tvSearchTimeToMinute.text = getString(R.string._00)
            }
            else{
                binding.tvSearchTimeToHour.text = it.substring(0,2)
                binding.tvSearchTimeToMinute.text = it.substring(2)
            }
        }
    }

    //인풋이 Time Picker로 변하면서 필요 없어짐
//        binding.apply {
//            edSearchTimeFrom.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//
//                    activity?.let { hideKeyBoard(it) }
//
//                    return@OnKeyListener true
//                }
//                false
//            })
//
//            edSearchTimeFrom2.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//
//                    activity?.let { hideKeyBoard(it) }
//
//                    return@OnKeyListener true
//                }
//                false
//            })
//
//            edSearchTimeTo.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//
//                    activity?.let { hideKeyBoard(it) }
//
//                    return@OnKeyListener true
//                }
//                false
//            })
//
//            edSearchTimeTo2.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//
//                    activity?.let { hideKeyBoard(it) }
//
//                    return@OnKeyListener true
//                }
//                false
//            })

//            edSearchTimeTo2.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//                    val searchTransportationFragment = SearchTransportationFragment()
//                    val transaction = parentFragmentManager.beginTransaction()
//                    transaction.replace(R.id.fr_main, searchTransportationFragment)
//                    transaction.addToBackStack(null)
//                    transaction.commit()
//
//                    activity?.let { hideKeyBoard(it) }
//
//                    return@OnKeyListener true
//                }
//                false
//            })
//        }



    private fun hideKeyBoard(activity: Activity) {
        val keyBoard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    private fun putViewModelData() {
        viewModel.getDustData()
        viewModel.getWeatherData(localDateTime)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLocalTime(){
        val time: Long = System.currentTimeMillis()
        val timeFormat = SimpleDateFormat("yyyy-MM-dd")
        today = timeFormat.format(Date(time))
        localDateTime = time.toString()
        Log.d("시간 확인", localDateTime)
    }

    //Time Picker 출력 메소드
    @SuppressLint("InflateParams")
    private fun createTimePickerBottomView() {

        var startTime : String = getString(R.string.search_start_time)
        var endTime : String = getString(R.string.search_end_time)

        viewModel.getStartTime(startTime)
        viewModel.getEndTime(endTime)
        val startTimePicker = timePickerBottomSheet.findViewById<TimePicker>(R.id.time_insert_start)
        val endTimePicker = timePickerBottomSheet.findViewById<TimePicker>(R.id.time_insert_end)
        val insertButton = timePickerBottomSheet.findViewById<TextView>(R.id.tv_time_insert_dismiss)
        timePickerBottomSheetView.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        startTimePicker.hour = 10
        startTimePicker.minute = 0
        endTimePicker.hour = 22
        endTimePicker.minute = 0

        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            startTime = today + "-" + getTimeString(hourOfDay) + " : " + getTimeString(minute)
            Log.d("from 시간", startTime)
            viewModel.getStartTime(startTime)
        }
        endTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            endTime = getTimeString(hourOfDay) + getTimeString(minute)
            Log.d("to 시간", endTime)
            viewModel.getEndTime(endTime)
        }

        insertButton.setOnClickListener {
            viewModel.getTimeStamp(startTime,endTime)
            timePickerBottomSheetView.dismiss()
        }
        timePickerBottomSheetView.setCancelable(false)
        timePickerBottomSheetView.show()
    }

    private fun getTimeString(data: Int) : String {
        var text : String = ""
        if(data < 10){
            text = "0$data"
        }
        else {
            text = "$data"
        }
        return text
    }

}