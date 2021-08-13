package com.example.livechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        supportFragmentManager.beginTransaction().replace(R.id.layout, FriendsFragment()).commit()

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_friends ->{
                    supportFragmentManager.beginTransaction().replace(R.id.layout, FriendsFragment()).commit()
                    true
                }
                R.id.page_profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.layout, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}