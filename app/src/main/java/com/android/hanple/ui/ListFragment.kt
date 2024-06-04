// ListFragment.kt
package com.android.hanple.ui

import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.hanple.adapter.AddressAdapter
import com.android.hanple.R
import com.android.hanple.databinding.FragmentListViewBinding
import com.example.mapdemo.MapFragment

class ListFragment : Fragment(), AddressAdapter.OnItemClickListener {
    private lateinit var binding: FragmentListViewBinding
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressList = getAddressList() // 주소 리스트 가져오기
        addressAdapter = AddressAdapter(addressList, this)

        binding.recyclerviewList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewList.adapter = addressAdapter
    }

    private fun getAddressList(): List<Address> {
        // 주소 리스트를 반환하는 메소드
        // 예시로 빈 리스트 반환
        return emptyList()
    }

    override fun onItemClick(address: Address) {
        // 다른 작업 수행
    }

    override fun onAddressClick(address: Address) {
        // 텍스트뷰 클릭 시 MapFragment로 전환
        val bundle = Bundle().apply {
            putDouble("lat", address.latitude)
            putDouble("lng", address.longitude)
        }
        val mapFragment = MapFragment().apply {
            arguments = bundle
        }
        parentFragmentManager.commit {
            replace(R.id.fragment_container, mapFragment)
            addToBackStack(null)
        }
    }
}
