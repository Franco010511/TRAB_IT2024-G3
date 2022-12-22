package com.example.anbiel.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anbielphoto.Model
import com.example.anbielphoto.R

class MyAdapter( var userList: ArrayList<Model> ): RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ImageViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = userList[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = userList.size

}