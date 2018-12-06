package com.esri.itworx.esri

import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.LocatorTask

interface NetworkHandlerInterface{
    fun returnNetworkLocatorTask(locatorTask: LocatorTask)
    fun returnGeocodeParameters(geocodeParameters: GeocodeParameters)
}