package com.example.umimaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.umimaps.databinding.ActivityDisplayMapBinding
import com.example.umimaps.models.UserMap
import com.google.android.gms.maps.model.LatLngBounds

class DisplayMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayMapBinding
    private lateinit var userMap: UserMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userMap = intent.getSerializableExtra(EXTRA_USER_MAP) as UserMap

        supportActionBar?.title = userMap.title
        binding = ActivityDisplayMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val boundsToCenter = LatLngBounds.Builder()
        for(place in userMap.places){
            var pos = LatLng(place.latitude,place.longitude)
            boundsToCenter.include(pos)
            mMap.addMarker(MarkerOptions().position(pos).title(place.title).snippet(place.description))
        }
        // Add a marker in Sydney and move the camera
        val Meknes = LatLng(33.857241, -5.5800908)
        mMap.addMarker(MarkerOptions().position(Meknes).title("Marker in ESTM"))

        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsToCenter.build(),800,800,0))
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsToCenter.build(),200,200,0))

    }
}