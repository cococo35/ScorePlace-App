package com.android.hanple.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSettingsBinding
import com.android.hanple.ui.ArchiveActivity
import com.android.hanple.ui.ListViewFragment
import com.android.hanple.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 클릭 이벤트 설정
        clickSearchHistory(binding)
        clickBookmarks(binding)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickSearchHistory(binding: FragmentSettingsBinding) {
        binding.tvSearchHistoy.setOnClickListener {
            val intent: Intent = Intent(activity, ArchiveActivity::class.java)
            intent.putExtra("key", "검색 기록을 눌러 ArchiveActivity로 이동하셨습니다.")
            startActivity(intent)
        }
    }

    private fun clickBookmarks(binding: FragmentSettingsBinding) {
        binding.tvBookmarks.setOnClickListener {
            // ListViewFragment로 이동하는 코드
            val listViewFragment = ListViewFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.listViewFragment, listViewFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
