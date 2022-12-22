package com.example.anbielphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.anbielphoto.databinding.ActivityDetailBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    var firebaseDatabase: FirebaseDatabase? = null
    var firebaseStorage: FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val image = findViewById<ImageView>(R.id.imageDetail)
        val imageDetail = intent.getStringExtra("image")
        Picasso.get().load(getIntent().getStringExtra("image")).into(image)

        binding.back.setOnClickListener {
            finish()
        }
    }
}