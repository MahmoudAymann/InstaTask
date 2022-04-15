package com.instabug.task.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.instabug.task.R
import com.instabug.task.base.InstaUtil
import com.instabug.task.base.show
import com.instabug.task.databinding.ActivityMainBinding
import com.instabug.task.helper.SearchMenuHelper
import com.instabug.task.ui.adapter.ItemMain
import com.instabug.task.ui.adapter.MainAdapter
import com.instabug.task.ui.adapter.MainAdapterCallBack
import com.instabug.task.ui.adapter.SortState
import com.instabug.task.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    var searchListener: SearchView.OnQueryTextListener? = null
    val adapter by lazy {
        MainAdapter(adapterCallBack).apply {
            setList(ItemMain.dummy().toMutableList())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleViewModelState()
        initSearchListener()
    }

    private fun initSearchListener() {
        searchListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        }
    }

    private fun handleViewModelState() {
        viewModel.uiState.observe(this) {
            when (it) {
                MainUiState.Init -> {
                    initRecyclerView()
                }
                is MainUiState.Loading -> binding.progressBar.show(it.isLoading)

            }
        }
    }


    private fun initRecyclerView() {
        binding.rvItems.let { rv ->
            val decoration = DividerItemDecoration(
                this,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
            rv.addItemDecoration(decoration)
            rv.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.forEach {
            InstaUtil.tintMenuIcon(this, it, R.color.white)
        }
        SearchMenuHelper(menu, searchListener)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_sort -> {
            adapter.sortList(if (adapter.sortState == SortState.ASC) SortState.DESC else SortState.ASC)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    private val adapterCallBack: MainAdapterCallBack by lazy {
        object : MainAdapterCallBack {
            override fun isEmpty(empty: Boolean) {
                binding.emptyView.root.show(empty)
            }
        }
    }

}