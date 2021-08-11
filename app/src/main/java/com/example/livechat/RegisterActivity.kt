package com.example.livechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        register_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            register_btn -> registerUser()
        }
    }

    private fun registerUser(){
        var name = edittext_name.text.toString()
        var email = edittext_email.text.toString()
        var password = edittext_password.text.toString()

        if(name.isEmpty()){
            edittext_name.setError("請輸入暱稱")
            edittext_name.requestFocus()
            return
        }

        if(email.isEmpty()){
            edittext_email.setError("請輸入電子信箱")
            edittext_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edittext_email.setError("請輸入有效的電子信箱")
            edittext_email.requestFocus()
            return
        }

        if(password.isEmpty()){
            edittext_password.setError("請輸入密碼")
            edittext_password.requestFocus()
            return
        }

        if(password.length < 6){
            edittext_password.setError("密碼長度必須至少為6個字元")
            edittext_password.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                val currentUser = auth.currentUser
                val currentUserDb =  databaseReference?.child(currentUser?.uid!!)
                currentUserDb?.child("Name")?.setValue(name)
                currentUserDb?.child("Email")?.setValue(email)

                Toast.makeText(this, "註冊成功", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "註冊失敗，再試一次", Toast.LENGTH_LONG).show()
            }
        }
    }
}