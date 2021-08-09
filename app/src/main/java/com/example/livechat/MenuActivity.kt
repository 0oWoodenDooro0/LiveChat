package com.example.livechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class MenuActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = FirebaseAuth.getInstance()

        supportFragmentManager.beginTransaction().replace(R.id.layout, FriendsFragment()).commit()

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_friends ->{
                    supportFragmentManager.beginTransaction().replace(R.id.layout, FriendsFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.page_profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.layout, ProfileFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
        check()
    }

    private fun check(){
        val user = auth.currentUser

        if(user == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}