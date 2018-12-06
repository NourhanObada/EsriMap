package com.esri.itworx.esri.view

import com.esri.arcgisruntime.tasks.geocode.GeocodeResult

interface SearchListViewModelInterface {
    fun displaySearchResults(searchResultsList:List<GeocodeResult>)
    fun displayFailureMessage(failMessage:String)
}