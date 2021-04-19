package com.example.embracecrash

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.embracecrash.App.Companion.CRASH
import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.databinding.LogItemBinding

class LogViewAdapter(var logs: List<LogEntity>) : RecyclerView.Adapter<LogViewAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return logs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewAdapter.ViewHolder {
        return ViewHolder(
            parent.context,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.log_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(logs[position])
    }

    inner class ViewHolder(private val context: Context, private val viewDataBinding: LogItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bindViewHolder(entry: LogEntity) {
            if(entry.type == CRASH){
                viewDataBinding.ts.setTextColor(Color.RED)
                viewDataBinding.type.setTextColor(Color.RED)
                viewDataBinding.desc.setTextColor(Color.RED)
            }
            viewDataBinding.ts.text = entry.timeStamp.toString()
            viewDataBinding.type.text = entry.type
            viewDataBinding.desc.text = entry.description
        }
    }
}