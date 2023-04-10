package com.exercicios.namesrepository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class NameAdapter(val list: MutableList<String>): RecyclerView.Adapter<NameAdapter.MyHolder>() {

    var onItemClickRecyclerView: OnItemClickRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val name = this.list[position]
        holder.tvName.text = name
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun add(name: String) {
        this.list.add(name)
        this.notifyDataSetChanged()
    }

    fun del(index: Int) {
        this.list.removeAt(index)
        this.notifyItemRemoved(index)
        this.notifyItemRangeChanged(index, this.list.size)
    }

    fun move(from: Int, to: Int) {
        Collections.swap(this.list, from, to)
        this.notifyItemMoved(from, to)
    }

    inner class MyHolder(item: View): RecyclerView.ViewHolder(item) {
        var tvName: TextView

        init {
            this.tvName = item.findViewById(R.id.tvName)
            item.setOnClickListener {
                this@NameAdapter.onItemClickRecyclerView?.onItemClick(this.adapterPosition)
            }
        }
    }
}