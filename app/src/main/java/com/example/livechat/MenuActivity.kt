package com.example.livechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(){

    lateinit var manager: FragmentManager
    lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        manager = supportFragmentManager
        showFragment(FriendsFragment(),"friendsfragment")
        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_friends ->{
                    //supportFragmentManager.beginTransaction().replace(R.id.layout, FriendsFragment()).commit()
                    transaction = manager.beginTransaction()
                    if (ProfileFragment() != null){
                        transaction.hide(ProfileFragment())
                    }
                    if(FriendsFragment().isAdded()){
                        transaction.show(FriendsFragment())
                    }
                    else{
                        transaction.add(R.id.layout, FriendsFragment())
                    }
                    transaction.commit()
                    true
                }
                R.id.page_profile ->{
                    //supportFragmentManager.beginTransaction().replace(R.id.layout, ProfileFragment()).commit()
                    transaction = manager.beginTransaction()
                    if (FriendsFragment() != null){
                        transaction.hide(FriendsFragment())
                    }
                    if(ProfileFragment().isAdded()){
                        transaction.show(ProfileFragment())
                    }
                    else{
                        transaction.add(R.id.layout, ProfileFragment())
                    }
                    transaction.commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String){
        var transaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null){
            transaction.add(R.id.layout, fragment, tag)
        }
        else{
            transaction.show(fragment)
        }
        transaction.commit()
    }

    private fun hideFragment(frag: Fragment){
        var transaction = manager.beginTransaction()
        transaction.hide(frag)
        transaction.commit()
    }
}