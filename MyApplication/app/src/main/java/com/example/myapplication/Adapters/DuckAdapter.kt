package com.example.myapplication.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.DuckRowLayoutBinding

import com.example.myapplication.models.duck.Result

class DuckAdapter : RecyclerView.Adapter<DuckAdapter.MyViewHolder>() {

    private var duck = emptyList<Result>()

    class MyViewHolder(private val binding: DuckRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DuckRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return DuckAdapter.MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = duck[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return duck.size
    }
}