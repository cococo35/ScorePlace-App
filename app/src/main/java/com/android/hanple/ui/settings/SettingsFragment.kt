package com.android.hanple.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.ArchiveActivity
import com.android.hanple.MainActivity
import com.android.hanple.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    //get()이랑 그냥 binding = _binding!!랑 다름. 게터로 값 선언 시, 해당 변수 불러올 때마다 갱신됨. https://stackoverflow.com/questions/76836641/what-is-the-difference-between-get-and-direct-assignment-in-kotlin

    //fragment 생명주기에 맞게 binding 생성, onDestroyView에서 파괴
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]
        //뷰모델과 연결하는 정석적인 방식. 다르게는 by viewModels 디펜던시로 가져와서 사용 가능.

        _binding = FragmentSettingsBinding.inflate(inflater, container, false) //바인딩 값 설정
        val root: View = binding.root //root는 return해서 onCreateView에 먹여 화면에 binding이 연결된다.

//    val textView: TextView = binding.textSettings
//    settingsViewModel.text.observe(viewLifecycleOwner) {
//      textView.text = it
//    } ViewModel 정보가 필요할 때. 현재 layout xml에서 @id/text_settings는 visible = gone으로 안 보이게 설정했기 때문에,
//    주석을 해제해도 바로 화면에 보이지는 않는다. visible = true로 변경하면 viewModel을 통해 text String이 전달된다.

        //TODO: 이렇게 주석을 작성하면, TODO 항목에서 할 일을 몰아 볼 수 있다.
        //TODO: 각 textView 클릭 시 다음 페이지로 넘어가기.
        clickSearchHistory(binding)
        clickBookmarks(binding)
//        clickLogout(binding)
//        clickSignOff(binding)
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
            val intent: Intent = Intent(activity, ArchiveActivity::class.java)
            intent.putExtra("key", "보관함을 눌러 ArchiveActivity로 이동하셨습니다.")
            startActivity(intent)
        }
    }

    private fun clickBackButton(binding: FragmentSettingsBinding) {
        binding.ivBackButton.setOnClickListener{
            //뒤로가기
        }
    }
}