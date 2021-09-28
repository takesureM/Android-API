package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CatRowLayoutBinding
import com.example.myapplication.models.cat.Cat
import com.example.myapplication.models.cat.Result
import com.example.myapplication.util.CatDiffUtil

class CatAdapter : RecyclerView.Adapter<CatAdapter.MyViewHolder>() {

    private var cat = emptyList<Result>()

    class MyViewHolder(private val binding: CatRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CatRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = cat[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return cat.size
    }

    fun setData(newCat: Cat) {
        val catDiffUtil = CatDiffUtil(cat, newCat.results)
        val diffUtilResult = DiffUtil.calculateDiff(catDiffUtil)
        cat = newCat.results
        diffUtilResult.dispatchUpdatesTo(this)

    }

}