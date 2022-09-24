package com.sapin.themovies.ui.location

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sapin.themovies.R

class MapsFragment : Fragment() {
    private lateinit var map:GoogleMap
     private var latitude:Double = 0.0
    private  var longitude:Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        longitude= requireArguments().getDouble(LONGITUDE)
        latitude= requireArguments().getDouble(LATITUDE)
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map=googleMap
        val sydney = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18f),4000,null)
    }

}