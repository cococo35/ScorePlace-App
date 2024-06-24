package com.scoreplace.hanple.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivityPrivatePolicyBinding

class PrivatePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivatePolicyBinding
    private val viewModel: PrivatePolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Hanple) //앱 시작 스플래시
        binding = ActivityPrivatePolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //View -> ViewModel로 정보 전송
        binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
            val isAtBottom: Boolean
                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
            viewModel.updateReadAll(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
        }

        //ViewModel -> View 정보 받아오기
        viewModel.scrolled.observe(viewLifecycleOwner) { isChecked -> //ViewModel에서
            binding.btnYes.isEnabled = isChecked
            changeButtonText(isChecked)
        }

        binding.btnYes.setOnClickListener{
            viewModel.updateAgreeOnPrivacyPolicy(true)
            findNavController().navigateUp() }
    }
        private fun changeButtonText(isChecked: Boolean) { //텍스트는 color와 달리 selector 없어 여기 작성함.
        if (isChecked) {
            binding.btnYes.text = getString(R.string.privacy_policy_scrolled)
        } else binding.btnYes.text = getString(R.string.privacy_policy_unscrolled)
    }
}


//class PrivacyPolicyFragment : Fragment() {
//
//    private var _binding: FragmentPrivacyPolicyBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel: OnboardViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
//
//        //View -> ViewModel로 정보 전송
//        binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
//            val isAtBottom: Boolean
//                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
//            viewModel.updateReadAll(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
//        }
//
//        //ViewModel -> View 정보 받아오기
//        viewModel.scrolled.observe(viewLifecycleOwner) { isChecked -> //ViewModel에서
//            binding.btnYes.isEnabled = isChecked
//            changeButtonText(isChecked)
//        }
//
//        binding.ivBack.setOnClickListener{
//            viewModel.updateAgreeOnPrivacyPolicy(false)
//            findNavController().navigateUp() }
//        binding.btnYes.setOnClickListener{
//            viewModel.updateAgreeOnPrivacyPolicy(true)
//            findNavController().navigateUp() }
//        return binding.root
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun changeButtonText(isChecked: Boolean) { //텍스트는 color와 달리 selector 없어 여기 작성함.
//        if (isChecked) {
//            binding.btnYes.text = getString(R.string.privacy_policy_scrolled)
//        } else binding.btnYes.text = getString(R.string.privacy_policy_unscrolled)
//    }