package com.android.hanple.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.hanple.databinding.FragmentMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val TAG = "MapFragment"
        private const val ARG_ADDRESS = "address"

        fun newInstance(address: String): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putString(ARG_ADDRESS, address)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            address = it.getString(ARG_ADDRESS)
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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        val latLng = LatLng(37.5562, 126.9724) // 서울역 위치
        currentMarker = setupMarker(latLng, address ?: "Unknown Location")
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(positionLatLng: LatLng, title: String): Marker? {
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title(title)
            snippet("위치: $title")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        return googleMap.addMarker(markerOption)
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
}
