package com.example.breezil.giffs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breezil.giffs.callbacks.QuickSearchListener
import com.example.breezil.giffs.databinding.SingleQuckSearchBinding

class QuickSearchRecyclerListAdapter(internal var searchList : List<String>, internal var quickSearchListener: QuickSearchListener)
    : RecyclerView.Adapter<QuickSearchRecyclerListAdapter.QuickSearchHolder>() {

    internal lateinit var binding : SingleQuckSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickSearchHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = SingleQuckSearchBinding.inflate(layoutInflater, parent, false)

        return QuickSearchHolder(binding)
    }


    override fun onBindViewHolder(holder: QuickSearchHolder, position: Int) {
        val string = searchList[position]
        holder.bind(string, quickSearchListener)
    }

    fun setList(search: List<String>) {
        searchList = search
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (searchList == null) {
            0
        } else searchList.size
    }

    inner class QuickSearchHolder(internal var binding: SingleQuckSearchBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(string: String, quickSearchListener: QuickSearchListener) {
            itemView.setOnClickListener { v -> quickSearchListener.getString(string) }

            binding.searchText.setText(string)
        }
    }
}


