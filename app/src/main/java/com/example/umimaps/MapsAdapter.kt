package com.example.umimaps

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.umimaps.models.UserMap

class MapsAdapter(val context: Context, val userMaps: List<UserMap>,val onClickListener: OnClickListener) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {
    interface OnClickListener{
        fun onItemClick(position:Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false)
        view.setBackgroundResource(R.drawable.border)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userMap = userMaps[position]
        holder.itemView.setOnClickListener{
            //msg
            onClickListener.onItemClick(position)
//            holder.itemView.setBackgroundColor(Color.parseColor("#1881a7"))

        }

        val textViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text = userMap.title
    }

    override fun getItemCount() = userMaps.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

}
