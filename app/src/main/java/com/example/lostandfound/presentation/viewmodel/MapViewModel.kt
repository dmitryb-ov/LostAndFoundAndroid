package com.example.lostandfound.presentation.viewmodel

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.example.lostandfound.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_map.view.*

class MapViewModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {
    private val yandexMap: MapView
    private var mapObjectCollection: MapObjectCollection
    private var markerTapListener: MapObjectTapListener? = null
    private val userLocation by lazy { getUserLocationLayer() }

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_map, this, true)
        yandexMap = map
        mapObjectCollection = yandexMap.map.mapObjects.addCollection()
    }

    fun onStart() {
        yandexMap.onStart()
        MapKitFactory.getInstance().onStart()
    }

    fun onStop() {
        yandexMap.onStop()
        MapKitFactory.getInstance().onStop()
    }

    fun release() {
        mapObjectCollection.clear()
        markerTapListener = null
    }

    fun setTapListener(listener: MapObjectTapListener) {
        markerTapListener = listener
    }

    fun showUserLocation() {
        userLocation.apply {
            isVisible = true
            isHeadingEnabled = true

            setAnchor(
                PointF((yandexMap.width * 0.5f), (yandexMap.height * 0.5f)),
                PointF((yandexMap.width * 0.5f), (yandexMap.height * 0.83f))
            )
            resetAnchor()
        }
    }

    fun moveAnimatedTo(
        latitude: Double,
        longitude: Double,
        zoom: Float = DEFAULT_ZOOM,
        azimuth: Float = 0.0f,
        tilt: Float = 0.0f,
        animation: Animation,
        callback: Map.CameraCallback? = null

    ) {
        yandexMap.map.move(
            CameraPosition(Point(latitude, longitude), zoom, azimuth, tilt),
            animation,
            callback
        )
    }

    fun addMarker(
        latitude: Double,
        longitude: Double,
        @DrawableRes imageRes: Int,
        userData: Any? = null
    ): PlacemarkMapObject {
        val marker = mapObjectCollection.addPlacemark(
            Point(latitude, longitude),
            ImageProvider.fromResource(context, imageRes)
        )
        marker.userData = userData
        markerTapListener?.let { marker.addTapListener(it) }
        return marker
    }

    fun getZoom() = yandexMap.map.cameraPosition.zoom

    private fun getUserLocationLayer() =
        MapKitFactory.getInstance().createUserLocationLayer(yandexMap.mapWindow)

    companion object {

        const val DEFAULT_ZOOM = 11.0f

        fun initialize(context: Context, apiKey: String) {
            MapKitFactory.setApiKey(apiKey)
            MapKitFactory.initialize(context)
        }
    }

}