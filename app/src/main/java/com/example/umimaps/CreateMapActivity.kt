package com.example.umimaps

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.umimaps.databinding.ActivityCreateMapBinding
import com.example.umimaps.models.Place
import com.example.umimaps.models.UserMap
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private var markers:MutableList<Marker> = mutableListOf ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.view?.let {//excute if not null
            Snackbar.make(it,"Long press to add a marker! ",Snackbar.LENGTH_INDEFINITE)
                .setAction("OK",{})//dismiss the snack after clicking ok
                .setActionTextColor(ContextCompat.getColor(this,android.R.color.white))
                .show()
        }

    }

    /**
     * Manipulates the map once available.
 e user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        mMap.setOnInfoWindowClickListener { markerToDelete ->
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }
        // Add a marker in Sydney and move the camera
        mMap.setOnMapLongClickListener { latLng ->
            showAlertDialog(latLng)
        }
        val currentPosEst = LatLng(33.857241, -5.5800908)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosEst,18F))
    }
    private fun showAlertDialog(latLng : LatLng){
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place,null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create a marker")
            .setView(placeFormView)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("ok",null)
            .show()

        //when clicking on the positive button
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()
            val descri = placeFormView.findViewById<EditText>(R.id.etDescription).text.toString()
            if(title.trim().isEmpty() ||descri.trim().isEmpty()){
                Toast.makeText(this,"Place must have non-empty title and description",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(descri))
            if (marker != null) {
                markers.add(marker)
            }
            dialog.dismiss()
        }

    }
    //for the save button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_map,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.save_btn){
            if(markers.isEmpty()){
                Toast.makeText(this,"There must be at least one marker on the map",Toast.LENGTH_LONG).show()
                return true
            }
            //iterat thro the list and add to places the markers
            val places = markers.map{
                marker-> Place(marker.title.toString(),marker.snippet.toString(),marker.position.latitude,marker.position.longitude)
            }
            val userMap = UserMap(intent.getStringExtra(EXTRA_MAP_TITLE).toString(),places)
            val data = Intent()
            data.putExtra(EXTRA_USER_MAP,userMap)
            setResult(Activity.RESULT_OK,data)
            finish()
            return true

        }
        return super.onOptionsItemSelected(item)
    }


}