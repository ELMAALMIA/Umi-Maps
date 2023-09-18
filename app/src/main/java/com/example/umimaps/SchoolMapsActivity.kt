package com.example.umimaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.umimaps.databinding.ActivitySchoolMapsBinding
import com.example.umimaps.models.UserMap
import com.google.android.gms.maps.model.LatLngBounds

class SchoolMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var positionWanted: UserMap
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySchoolMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySchoolMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        positionWanted= intent.getSerializableExtra(POSITION) as UserMap
        supportActionBar?.title = positionWanted.title

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        // Add a marker in Sydney and move the camera
        val pos = LatLng(positionWanted.places[0].latitude, positionWanted.places[0].longitude)
        mMap.addMarker(MarkerOptions().position(pos).title(positionWanted.title).snippet(positionWanted.places[0].description))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,18F))
    }
}