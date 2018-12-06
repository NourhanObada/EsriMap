package com.esri.itworx.esri.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.esri.itworx.esri.*
import com.esri.itworx.esri.viewModel.SearchListViewModel
import kotlinx.android.synthetic.main.search_result_list.*

class SearchActivity : AppCompatActivity(), SearchListViewModelInterface {


    private lateinit var searchView: SearchView
    private lateinit var searchListViewModel: SearchListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result_list)
        searchListViewModel = SearchListViewModel(this)
        searchListViewModel.setupLocator()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchMenuItem = menu.findItem(R.id.search)
        if (searchMenuItem != null) {
            searchView = searchMenuItem.actionView as SearchView
            if (searchView != null) {
                val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.setIconifiedByDefault(true)
            }
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            searchListViewModel.queryLocator(intent.getStringExtra(SearchManager.QUERY))
        }
    }

    override fun displaySearchResults(searchResultsList: List<GeocodeResult>) {
        var searchAdapter = SearchResultsListAdapter(searchResultsList)
        searchResultList.adapter = searchAdapter
        searchResultList.visibility = View.VISIBLE
        displaySelectedLocation(searchAdapter)
    }

    override fun displayFailureMessage(failMessage: String) {
        Toast.makeText(this, failMessage, Toast.LENGTH_LONG).show()
    }

    private fun displaySelectedLocation(adapter: SearchResultsListAdapter) {
        adapter.searchListRecyclerViewAdapterListener = object : SearchListRecyclerViewAdapterListener {
            override fun onItemClick(location: GeocodeResult) {
                val mapActivityIntent = Intent(this@SearchActivity, MapActivity::class.java)
                mapActivityIntent.putExtra("Longitude",location.displayLocation.x)
                mapActivityIntent.putExtra("Latitude",location.displayLocation.y)
                startActivity(mapActivityIntent)
            }
        }
    }

}
