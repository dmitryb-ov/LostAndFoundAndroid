package com.example.lostandfound.presentation.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostandfound.R
import com.example.lostandfound.data.model.Marker
import com.example.lostandfound.presentation.viewmodel.MapViewModel
import com.google.android.gms.location.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import kotlinx.android.synthetic.main.activity_map.view.*


class MapActivity : AppCompatActivity() {
    private lateinit var mapView: MapViewModel
    private val markerDataList = HashMap<Marker, PlacemarkMapObject>()
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapViewModel.initialize(applicationContext, KEY)
        setContentView(R.layout.activity_map)

        mapView = MapViewModel(applicationContext, null, 0, 0)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

//        latitude = 57.657158
//        longitude = 47.872408
        mapView.showUserLocation()
        mapView.moveAnimatedTo(
            latitude = latitude, longitude = longitude, animation = Animation(
                Animation.Type.SMOOTH,
                0.0f
            )
        )

        mapView.map.map.addInputListener(tapListener)
    }

    val tapListener = object : InputListener {
        override fun onMapLongTap(p0: Map, p1: Point) {

        }

        override fun onMapTap(p0: Map, p1: Point) {
            addMarkers(
                arrayListOf(
                    Marker(
                        p1.latitude,
                        p1.longitude,
                        R.drawable.ic_baseline_location_on_24,
                        ""
                    )
                )
            )
        }


    }

    override fun onStart() {
        super.onStart()
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                mapView.onStart()
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    , 1
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        locationClient.lastLocation.addOnCompleteListener {
            location = it.result!!
            requestNewLocationData()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            latitude = lastLocation.latitude
            longitude = lastLocation.longitude
        }
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.release()
    }

    private fun addMarkers(dataList: List<Marker>) {
        for (data in dataList) {
            val marker = mapView.addMarker(
                latitude = data.latitude,
                longitude = data.longitude,
                imageRes = R.drawable.ic_baseline_location_on_24,
                userData = data.lostId
            )
            markerDataList[data] = marker
        }
    }

    companion object {
        val KEY = "7c504409-52d7-4b27-9acc-5c49234c503b"
    }

}