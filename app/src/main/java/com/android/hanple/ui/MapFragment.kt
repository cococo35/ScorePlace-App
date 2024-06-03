package com.example.mapdemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.hanple.R
import com.android.hanple.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SupportMapFragment를 이용하여 지도를 설정
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initFragment()
    }

    // 프래그먼트 초기화 함수
    private fun initFragment() {
        Log.d("MapFragment", "Initializing fragment and adding marker.")
        // 마커를 추가할 위치를 설정 (예: 시드니)
        val sydney = LatLng(-34.0, 151.0)
        addMarker(sydney, "Marker in Sydney")
    }

    // 마커 추가 함수
    private fun addMarker(latLng: LatLng, title: String?) {
        mMap.addMarker(MarkerOptions().position(latLng).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        logMarkerDetails(latLng, title)
    }

    // 마커 정보를 로그로 출력하는 함수
    private fun logMarkerDetails(latLng: LatLng, title: String?) {
        Log.d("MapFragment", "Marker added at: ${latLng.latitude}, ${latLng.longitude} with title: $title")
    }
}
