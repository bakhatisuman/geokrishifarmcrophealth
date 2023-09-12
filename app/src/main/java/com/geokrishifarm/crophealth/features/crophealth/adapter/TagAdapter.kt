package com.geokrishifarm.crophealth.features.crophealth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.geokrishifarm.crophealth.R
import com.geokrishifarm.crophealth.features.crophealth.dto.TagItem


class TagAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private var context: Context
    private var list = ArrayList<TagItem>()
    private var listener: OnItemClick


    constructor(context: Context, listener: OnItemClick) : super() {
        this.context = context
        this.list = list
        this.listener = listener
    }

    interface OnItemClick {
        fun item(item: TagItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_crop_ls_tag, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvTag.text = item.tag

    }


    fun submitList(tagList: ArrayList<TagItem>) {
//        this.list.clear()
//        this.list.addAll(tagList)
        this.list = tagList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

        var tvTag: TextView

        init {
            context = itemView!!.context
            tvTag = itemView.findViewById(R.id.tag) as TextView


            itemView.setOnClickListener {
                val pos = this@ViewHolder.adapterPosition
                listener.item(list[pos])

            }

        }

    }

}


