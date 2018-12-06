package com.esri.itworx.esri.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.esri.itworx.esri.R

class MapActivity:AppCompatActivity(){

    private lateinit var mapView: MapView
    private var selectedLocationLongitude: Double = 0.0
    private var selectedLocationLatitude: Double = 0.0
    private var graphicsOverlay:GraphicsOverlay = GraphicsOverlay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_view)
        mapView = findViewById(R.id.mapView)
        getCoordinates(savedInstanceState)
        setupSelectedLocation(selectedLocationLongitude,selectedLocationLatitude)
    }

    private fun getCoordinates(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            val extras = intent.extras
            selectedLocationLongitude = extras.getDouble("Longitude")
            selectedLocationLatitude = extras.getDouble("Latitude")
        }else{
            selectedLocationLongitude = savedInstanceState.getSerializable("Longitude") as Double
            selectedLocationLatitude = savedInstanceState.getSerializable("Latitude") as Double
        }
    }

    private fun setupSelectedLocation(longitude: Double, latitude:Double) {
        if (mapView != null) {
            val basemapType = Basemap.Type.STREETS
            val displayLongitude = longitude
            val displayLatitude = latitude
            val levelOfDetail = 12
            val map = ArcGISMap(basemapType, displayLatitude, displayLongitude, levelOfDetail)
            addMapMarker(displayLongitude,displayLatitude)
            mapView.map = map
        }
    }
    private fun addMapMarker(longitude: Double,latitude: Double){
        mapView.graphicsOverlays.add(graphicsOverlay)
        val point = Point(longitude,latitude,SpatialReferences.getWgs84())
        val simpleMarker = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.X,Color.rgb(0,0,0),12.0f)
        val mapMarkerLayer = Graphic(point,simpleMarker)
        val allGraphics = graphicsOverlay.graphics
        allGraphics.add(mapMarkerLayer)
    }
}
