package com.esri.itworx.esri.viewModel

import android.arch.lifecycle.ViewModel
import com.esri.arcgisruntime.loadable.LoadStatus
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.LocatorTask
import com.esri.itworx.esri.NetworkHandler
import com.esri.itworx.esri.NetworkHandlerInterface
import com.esri.itworx.esri.view.SearchListViewModelInterface
import java.util.concurrent.ExecutionException

class SearchListViewModel(private val searchResults: SearchListViewModelInterface) : ViewModel() {

    private var locatorTask: LocatorTask? = null
    private var geocodeParameters: GeocodeParameters = GeocodeParameters()

    fun setupLocator() {
        val locatorLocation = "https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer"
        locatorTask = LocatorTask(locatorLocation)
        locatorTask!!.addDoneLoadingListener {
            if (locatorTask!!.loadStatus == LoadStatus.LOADED) {
                geocodeParameters.resultAttributeNames.add("*")
                geocodeParameters.maxResults = 10
            }
        }
        locatorTask!!.loadAsync()
    }

    fun queryLocator(query: String?) {
        if (query != null && query.isNotEmpty()) {
            locatorTask!!.cancelLoad()
            val geocodeFuture = locatorTask!!.geocodeAsync(query, geocodeParameters)
            geocodeFuture.addDoneListener(object : Runnable {
                override fun run() {
                    try {
                        val geocodeResults = geocodeFuture.get()
                        if (geocodeResults.size > 0) {
                            searchResults.displaySearchResults(geocodeResults)
                        } else {
                            searchResults.displayFailureMessage("Nothing found for $query")
                        }
                    } catch (e: InterruptedException) {
                    } catch (e: ExecutionException) {
                    }
                    geocodeFuture.removeDoneListener(this)
                }
            })
        }
    }
}