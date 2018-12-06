package com.esri.itworx.esri

import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.LocatorTask

class NetworkHandler(val networkHandlerInterface:NetworkHandlerInterface){

    private lateinit var locatorTask: LocatorTask
    private var geocodeParameters: GeocodeParameters = GeocodeParameters()

    fun setupLocator(){
        val locatorLocation = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer"
        locatorTask = LocatorTask(locatorLocation)
        locatorTask!!.addDoneLoadingListener {
            if (locatorTask!!.loadStatus == LoadStatus.LOADED) {
                geocodeParameters.resultAttributeNames.add("*")
                geocodeParameters.maxResults = 10
            }
        }
        locatorTask!!.loadAsync()
        networkHandlerInterface.returnNetworkLocatorTask(locatorTask)
        networkHandlerInterface.returnGeocodeParameters(geocodeParameters)
    }
}