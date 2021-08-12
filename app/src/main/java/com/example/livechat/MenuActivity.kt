package com.example.livechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import user

class MenuActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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
        check()
    }

    private fun check(){
        val currentUser = auth.currentUser

        if(currentUser == null){

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else{
            val currentUserDb =  databaseReference?.child(currentUser?.uid!!)
            user.uid = currentUser?.uid
            currentUserDb?.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user.email = snapshot.child("Email").value.toString()
                    user.name = snapshot.child("Name").value.toString()
                    user.sex = snapshot.child("Sex").value.toString()
                    user.birthday = snapshot.child("Birthday").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

}