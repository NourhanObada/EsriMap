package com.esri.itworx.esri.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.esri.itworx.esri.R

class SearchResultsListAdapter(private val searchResultsList: List<GeocodeResult>) :
        RecyclerView.Adapter<SearchResultsListAdapter.SearchListViewHolder>() {

    var searchListRecyclerViewAdapterListener: SearchListRecyclerViewAdapterListener? = null

    class SearchListViewHolder(mainView: View) : RecyclerView.ViewHolder(mainView) {
        val placeNameTextView: TextView = mainView.findViewById(R.id.searchResultName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val mainView = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false) as View
        return SearchListViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val searchResult = searchResultsList[position]
        holder.placeNameTextView.text = searchResult.label
        holder.itemView.setOnClickListener {
            if (searchListRecyclerViewAdapterListener != null) {
                searchListRecyclerViewAdapterListener!!.onItemClick(searchResult)
            }
        }
    }

    override fun getItemCount() = searchResultsList.size
}

interface SearchListRecyclerViewAdapterListener {
    fun onItemClick(location: GeocodeResult)
}
