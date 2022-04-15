package com.instabug.task.helper

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.instabug.task.R

class SearchMenuHelper(
    menu: Menu,
    searchViewListener: SearchView.OnQueryTextListener?
) {

    init {
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Do something when action item collapses
                Log.e("collapse", "true")
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do something when expanded
                Log.e("expand", "true")
                return true
            }
        }
        val searchItem = menu.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(expandListener)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(searchViewListener)
    }
}