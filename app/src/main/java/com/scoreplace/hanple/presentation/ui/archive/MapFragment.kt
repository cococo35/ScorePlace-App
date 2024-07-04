package com.scoreplace.hanple.ui.archive

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.scoreplace.hanple.data.CategoryPlace
import com.scoreplace.hanple.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val TAG = "MapFragment"
        private const val ARG_PLACES = "places"

        fun newInstance(places: Set<CategoryPlace>): MapFragment {
            val fragment = MapFragment()
            val args = Bundle().apply {
                putSerializable(ARG_PLACES, HashSet(places))
            }
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var places: Set<CategoryPlace>? = null
    private var markerMap: MutableMap<CategoryPlace, Marker> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("UNCHECKED_CAST")
            places = it.getSerializable(ARG_PLACES) as? Set<CategoryPlace>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MapFragment)

        binding.icBackbtn.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // 첫 번째 장소의 위치를 초기 카메라 위치로 설정
        if (!places.isNullOrEmpty()) {
            val firstPlace = places!!.first()
            val firstLatLng = getLatLngFromAddress(firstPlace.address ?: "")
            if (firstLatLng != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 15f))
                setupMarker(firstLatLng, firstPlace)
            }
        } else {
            // places가 비어있을 경우, 사용자가 원하는 기본 위치 설정 예시 (서울로 설정)
            val seoulLatLng = LatLng(37.5665, 126.9780)  // 서울의 위도와 경도
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulLatLng, 12f))
        }

        // 기존의 places를 이용한 마커 설정 로직
        places?.forEach { place ->
            val latLng = getLatLngFromAddress(place.address ?: "")
            if (latLng != null) {
                setupMarker(latLng, place)
            }
        }
    }



    private fun getLatLngFromAddress(address: String): LatLng? {
        val context = context ?: return null // context가 null인지 확인
        val geocoder = Geocoder(context)
        return try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val location = addresses[0]
                LatLng(location.latitude, location.longitude)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun setupMarker(positionLatLng: LatLng, place: CategoryPlace) {
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title(place.address)
            snippet("점수: ${place.score}")
        }
        val marker = googleMap.addMarker(markerOption)

        // markerMap에 값을 추가할 때는 put 메서드를 사용합니다.
        if (marker != null) {
            markerMap.put(place, marker)
        }

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroyView() {
        mapView.onDestroy()
        super.onDestroyView()
        _binding = null
    }

    fun removeMarker(place: CategoryPlace) {
        markerMap.remove(place)?.remove()
    }
}
