package com.example.twogisapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twogisapp.R
import com.example.twogisapp.domain.entities.Form

class VolunteersAdapter(
    private val onItemClick: (Form) -> Unit
) : RecyclerView.Adapter<VolunteersAdapter.MyViewHolder>() {

    val items = mutableListOf<Form>().apply {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.back_form,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.findViewById<ImageView>(R.id.icAvatar)
        val ivCategory = view.findViewById<ImageView>(R.id.ivInvalidType)
        val tvTime = view.findViewById<TextView>(R.id.tvTime)
        val tvFromR = view.findViewById<TextView>(R.id.tvFromReceived)
        val tvToR = view.findViewById<TextView>(R.id.tvToReceived)

        fun bind(item: Form) {
            tvTime.text = item.tvTime
            tvFromR.text = item.tvFromR
            tvToR.text = item.tvToR
            ivCategory.setImageResource(item.ivCategory)

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}