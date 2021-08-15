package com.example.livechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import user

class LoadingActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    var database : DatabaseReference? = null
    private var storage : StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference().child("profile")
        storage = FirebaseStorage.getInstance().getReference().child("profile")

        check()
    }

    private fun check(){
        val currentUser = auth.currentUser

        if(currentUser == null){

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else{
            val currentUserDb =  database?.child(currentUser?.uid!!)
            user.uid = currentUser?.uid
            currentUserDb?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user.email = snapshot.child("email").value.toString()
                    user.name = snapshot.child("name").value.toString()
                    user.sex = snapshot.child("sex").value.toString()
                    user.birthday = snapshot.child("birthday").value.toString()
                    user.imageurl = snapshot.child("imageurl").value.toString()
                    startActivity(Intent(this@LoadingActivity, MenuActivity::class.java))
                    finish()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}