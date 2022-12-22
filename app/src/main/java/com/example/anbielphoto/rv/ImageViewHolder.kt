package com.example.anbiel.rv


import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anbielphoto.DetailActivity
import com.example.anbielphoto.Model
import com.example.anbielphoto.databinding.ItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class ImageViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val binding = ItemBinding.bind(view)

    fun render(model: Model) {
        Glide.with(binding.ivSuperHero.context).load(model.image).into(binding.ivSuperHero)

        binding.ivSuperHero.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("image", model.image)
            context.startActivity(intent)
        }
    }
}


