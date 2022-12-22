package com.example.anbielphoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anbiel.rv.MyAdapter
import com.example.anbielphoto.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mDatabase : DatabaseReference
    lateinit var rv: RecyclerView
    lateinit var imageArray: ArrayList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getImageData()

        binding.button.setOnClickListener {
            startActivity(Intent(this, SelectorActivity::class.java))
        }
    }

    private fun getImageData() {
        rv = findViewById(R.id.recyclerView)
        rv.layoutManager = GridLayoutManager(this,4)
        rv.setHasFixedSize(true)
        imageArray = arrayListOf<Model>()

        mDatabase = FirebaseDatabase.getInstance().getReference("Imagenes")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (imageSnapshot in snapshot.children){
                        val image = imageSnapshot.getValue(Model::class.java)
                        imageArray.add(0,image!!)
                    }
                    rv.adapter = MyAdapter(imageArray)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}