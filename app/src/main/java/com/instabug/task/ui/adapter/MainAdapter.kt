package com.instabug.task.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instabug.task.databinding.ItemMainViewBinding

interface MainAdapterCallBack {
    fun isEmpty(empty: Boolean)
}

class MainAdapter(val mainAdapterCallBack: MainAdapterCallBack) :
    ListAdapter<ItemMain, MainAdapter.MainViewHolder>(MainDiffUtil()),
    Filterable {
    private var mCurrentList = mutableListOf<ItemMain>()
    var sortState = SortState.UNSORTED
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setList(listOfItems: MutableList<ItemMain>) {
        mCurrentList = listOfItems
        submitList(mCurrentList)
    }

    class MainViewHolder(private val binding: ItemMainViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): MainViewHolder {
                val binding = ItemMainViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return MainViewHolder(binding)
            }
        }

        fun bind(item: ItemMain) {
            binding.tvWord.text = item.word
            binding.tvCount.text = item.count.toString()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(text: CharSequence?): FilterResults {
                val filteredList = mutableListOf<ItemMain>()
                if (text.isNullOrEmpty()) {
                    filteredList.addAll(mCurrentList)
                } else {
                    mCurrentList.forEach { item ->
                        if (item.word.lowercase().contains(text.toString().lowercase())) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(text: CharSequence?, filterResults: FilterResults?) {
                try {
                    val listOfItems = filterResults?.values as MutableList<ItemMain>
                    mainAdapterCallBack.isEmpty(listOfItems.isNullOrEmpty())
                    submitList(listOfItems)
                } catch (e: Exception) {
                    Log.e("Adapter Filter", e.toString())
                }
            }

        }
    }

    override fun submitList(list: MutableList<ItemMain>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    fun sortList(sortState: SortState) {
        this.sortState = sortState
        when (sortState) {
            SortState.UNSORTED -> {
                mCurrentList.sortBy { it.id }
                submitList(mCurrentList)
            }
            SortState.ASC -> {
                mCurrentList.sortBy { it.count }
                submitList(mCurrentList)
            }
            SortState.DESC -> {
                mCurrentList.sortBy { it.count }
                submitList(mCurrentList)
            }
        }
    }
}

class MainDiffUtil : DiffUtil.ItemCallback<ItemMain>() {
    override fun areItemsTheSame(oldItem: ItemMain, newItem: ItemMain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemMain, newItem: ItemMain): Boolean {
        return oldItem == newItem
    }
}