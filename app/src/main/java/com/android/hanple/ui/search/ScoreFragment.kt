package com.android.hanple.ui.search

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.hanple.R
import com.android.hanple.Room.RecommendDataBase
import com.android.hanple.Room.recommendPlaceGoogleID
import com.android.hanple.data.CategoryPlace
import com.android.hanple.adapter.OnDataClick
import com.android.hanple.adapter.PlaceScoreCategoryAdapter
import com.android.hanple.adapter.ScoreCategoryListAdapter
import com.android.hanple.databinding.FragmentScoreBinding
import com.android.hanple.ui.ListViewFragment
import com.android.hanple.ui.MainActivity
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class ScoreFragment : Fragment() {
    private val binding by lazy {
        FragmentScoreBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private lateinit var callback: OnBackPressedCallback
    private val recommendDAO by lazy {
        RecommendDataBase.getMyRecommendPlaceDataBase(requireContext()).getMyRecommendPlaceDAO()
    }
    val typeList = listOf(
        "restaurant", "cafe", "parking", "movie_theater", "shopping_mall", "subway_station", "bus_station", "park"
    )

    val spinnerList = listOf(
        "음식점", "카페", "주차장", "영화관", "쇼핑몰", "지하철역", "버스정류장", "공원"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getScoreDescription()
        getWeatherDescription()
        initDetailDialog()
        onBackPressButton()
        setRecommendPlace()
        loadImage()
        createBottomView()
        binding.ivScoreBookmark.setOnClickListener {
            toggleBookmarkIcon()
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
                    clearBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {

        viewModel.getNearByPlace(typeList[0])
        binding.recyclerviewScoreCategory.adapter = PlaceScoreCategoryAdapter(object : OnDataClick {
            override fun onItemClick(data: CategoryPlace) {
                onSelectItemClick(data)
                initSelectPlaceDetailDialog()
            }
        })

        binding.recyclerviewScoreCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.nearByPlace.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recyclerviewScoreCategory.visibility = View.GONE
                binding.tvNotFound.visibility = View.VISIBLE
            } else {
                binding.recyclerviewScoreCategory.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.GONE
                (binding.recyclerviewScoreCategory.adapter as PlaceScoreCategoryAdapter).submitList(it)
            }
        }


        val localDateTime: LocalDateTime = LocalDateTime.now()
        val dateFormat = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        viewModel.selectPlace?.observe(viewLifecycleOwner) {
            binding.tvScoreTitle.text = "${it?.name}"
            binding.tvScoreTitle2.text =
                localDateTime.toString().substring(5, 7) +
                        "월 " +
                        localDateTime.toString().substring(8, 10) +
                        "일 " +
                        localDateTime.toString().substring(11, 16)
        }
        viewModel.totalScore.observe(viewLifecycleOwner) {
            binding.tvScoreScore.text = "${it}점"
        }
    }

    private fun getScoreDescription() {
        viewModel.totalScore.observe(viewLifecycleOwner) {
            when {
                it < 40 -> binding.tvScoreDescription.text = "해당 장소를 추천하지 않아요."
                it in 40..74 -> binding.tvScoreDescription.text = "놀러 가기 적당해요~"
                else -> binding.tvScoreDescription.text = "매우 추천합니다. 꼭 다녀오세요!"
            }
        }
    }

    private fun getWeatherDescription() {
        viewModel.readWeatherDescription.observe(viewLifecycleOwner) {
            when {
                it.contains("Rain") -> {
                    binding.tvScoreWeatherDescription.text = "비가 올 수 있어요"
                    binding.tvScoreWeatherDescription2.text = "우산을 준비하세요"
                    binding.ivScoreWeather.setBackgroundResource(R.drawable.ic_weather_rain)
                }
                !it.contains("Rain") && it.count { it.contains("Clouds") } >= 3 -> {
                    binding.tvScoreWeatherDescription.text = "전반적으로 날씨가 흐려요"
                    binding.tvScoreWeatherDescription2.text = ""
                    binding.ivScoreWeather.setBackgroundResource(R.drawable.iv_weather_cloud)
                }
                else -> {
                    binding.tvScoreWeatherDescription.text = "맑은 날씨에요"
                    binding.tvScoreWeatherDescription2.text = ""
                    binding.ivScoreWeather.setBackgroundResource(R.drawable.iv_weather_sun)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_detail_score_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val dialogCloseButton = dialog.findViewById<TextView>(R.id.tv_detail_dialog_dismiss)
        val scoreCost = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_cost)
        val scoreDust = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_dust)
        val scoreTraffic = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_traffic)
        val scoreCongestion = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_congestion)
        val scoreWeather = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_weather)
        viewModel.readCostScore.observe(viewLifecycleOwner) {
            scoreCost.text = "비용 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readDustScore.observe(viewLifecycleOwner) {
            scoreDust.text = "미세먼지 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readTransportScore.observe(viewLifecycleOwner) {
            scoreTraffic.text = "교통 점수 : ${"%.0f".format(it.toDouble() / 20 * 100)}점"
        }
        viewModel.readCongestScore.observe(viewLifecycleOwner) {
            scoreCongestion.text = "여행 성향 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        viewModel.readWeatherScore.observe(viewLifecycleOwner) {
            scoreWeather.text = "날씨 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        dialogCloseButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.tvScoreDetail.setOnClickListener {
            dialog.show()
        }
        dialog.setCancelable(false)
    }

    private fun onBackPressButton() {
        binding.ivScoreBack.setOnClickListener {
            clearBackStack()
        }
    }

    private fun clearBackStack() {
        AlertDialog.Builder(requireContext())
            .setMessage("처음 화면으로 돌아가시겠습니까?")
            .setPositiveButton("YES") { dialog, _ ->
                val fragmentManager: FragmentManager = parentFragmentManager
                val searchFragment = SearchFragment()
                viewModel.resetPlaceData()
                viewModel.resetScore()
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.to_left, R.anim.from_left)
                transaction.replace(R.id.fr_main, searchFragment)
                transaction.commit()
                viewModel.resetTimeStamp()
                viewModel.resetTime()
                (activity as MainActivity).visibleDrawerView()
            }
            .setNegativeButton("No", null)
            .create()
            .show()
    }

    private fun randomNumberPlace(): List<Int> {
        val edge = recommendPlaceGoogleID.size
        val list = mutableListOf<Int>()
        var number: Int
        while (list.size < 5) {
            number = Random.nextInt(edge) + 1
            if (!list.contains(number)) {
                list.add(number)
            }
        }
        return list
    }

    private fun setRecommendPlace() {
        val list = randomNumberPlace()
        viewModel.getRecommendPlace(list, recommendDAO)
    }

    private fun loadImage() {
        lifecycleScope.launch {
            whenStarted {
                while (true) {
                    delay(1000)
                    (binding.recyclerviewScoreCategory.adapter as PlaceScoreCategoryAdapter).notifyDataSetChanged()
                }
            }
        }
    }

    private fun toggleBookmarkIcon() {
        val isBookmarked = binding.ivScoreBookmark.tag == "bookmarked"
        if (isBookmarked) {
            binding.ivScoreBookmark.setImageResource(R.drawable.ic_bookmark_24dp) // 아이콘을 변경합니다.
            binding.ivScoreBookmark.tag = "not_bookmarked" // 태그를 변경합니다.
        } else {
            binding.ivScoreBookmark.setImageResource(R.drawable.ic_bookmark_filed) // 아이콘을 변경합니다.
            binding.ivScoreBookmark.tag = "bookmarked" // 태그를 변경합니다.
        }
    }

    @SuppressLint("InflateParams", "NotifyDataSetChanged")
    private fun createBottomView(){
        val scoreCategoryBottomSheet = layoutInflater.inflate(R.layout.fragment_score_category_bottom, null)
        val scoreCategoryBottomSheetView = BottomSheetDialog(requireContext())
        scoreCategoryBottomSheetView.setContentView(scoreCategoryBottomSheet)
        val bottomSheetList = scoreCategoryBottomSheet.findViewById<RecyclerView>(R.id.bottom_score_category_list)
        val bottomButton = scoreCategoryBottomSheet.findViewById<TextView>(R.id.tv_score_category_button)
        bottomSheetList.adapter = ScoreCategoryListAdapter(spinnerList, object : ScoreCategoryListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                viewModel.getNearByPlace(typeList[position])
                binding.btnCategoryViewOpen.text = spinnerList[position]
            }
        })
        scoreCategoryBottomSheetView.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetList.layoutManager = LinearLayoutManager(requireContext())
        bottomButton.setOnClickListener {
            scoreCategoryBottomSheetView.dismiss()

        }

        binding.btnCategoryViewOpen.setOnClickListener {
            scoreCategoryBottomSheetView.show()
        }
        scoreCategoryBottomSheetView.setCancelable(false)
    }

    private fun onSelectItemClick(data: CategoryPlace){
        viewModel.setSelectPlaceImg(data.img)
        data.name?.let { viewModel.setSelectPlaceName(it) }
        data.address?.let { viewModel.setSelectPlaceAddress(it) }
        data.description?.let { viewModel.setSelectPlaceSummary(it) }
        data.openingHours?.let { viewModel.setSelectPlaceOpeningHour(it) }
    }
    private fun initSelectPlaceDetailDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_detail_category_info_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val img = dialog.findViewById<AppCompatImageView>(R.id.tv_detail_category_info_img)
        val nullImg = dialog.findViewById<ImageView>(R.id.tv_detail_category_info_null_img)
        val name = dialog.findViewById<TextView>(R.id.tv_detail_category_info_title)
        val address = dialog.findViewById<TextView>(R.id.tv_detail_category_info_address)
        val summary = dialog.findViewById<TextView>(R.id.tv_detail_category_info_summary)
        val openingHour = dialog.findViewById<TextView>(R.id.tv_detail_category_info_openhour)
        val closeButton = dialog.findViewById<TextView>(R.id.tv_detail_category_info_dismiss)
        viewModel.selectCategoryPlaceImg.observe(viewLifecycleOwner){
            if(it == null){
                img.visibility = View.INVISIBLE
                nullImg.visibility = View.VISIBLE
            }
            else {
                Glide.with(requireContext()).load(it).into(img)
            }
        }
        viewModel.selectCategoryPlaceName.observe(viewLifecycleOwner){
            name.text = it
        }
        viewModel.selectCategoryPlaceAddress.observe(viewLifecycleOwner){
            if(it != null){
                address.text = it
            }
            else {
                address.text = ""
            }
        }

        //정보를 제공하는 장소가 너무 적어서 일단 빈 텍스트로 설정
        summary.text = ""
        openingHour.text = ""
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
}
