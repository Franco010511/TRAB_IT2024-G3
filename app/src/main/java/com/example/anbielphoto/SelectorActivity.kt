package com.example.anbielphoto

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.anbielphoto.databinding.ActivitySelectorBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SelectorActivity : AppCompatActivity() {
    var binding: ActivitySelectorBinding? = null
    var uri: Uri? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var firebaseStorage: FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectorBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        binding!!.ivSelector.setOnClickListener {
            uploadImage()
        }
        binding!!.btnSelectorSubir.setOnClickListener {
            alertdialog()
            subirimage()
        }
        binding!!.back.setOnClickListener {
            finish()
        }
    }

    private fun subirimage() {
        val reference = firebaseStorage!!.reference.child("Images").child(System.currentTimeMillis().toString() + "")
        reference.putFile(uri!!).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { uri ->
                val model = Model()
                model.image = uri.toString()
                firebaseDatabase!!.reference.child("Imagenes").push()
                    .setValue(model).addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SelectorActivity, "Error al Subir Imagen...", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun uploadImage() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, 101)
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    Toast.makeText(this@SelectorActivity, "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }
                override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest, permissionToken: PermissionToken) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            uri = data!!.data
            binding!!.ivSelector.setImageURI(uri)
        }
    }
    private fun alertdialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Subiendo Image")
        builder.setMessage("Espere un momento por favor...")
        builder.setIcon(R.drawable.logo_noti)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}