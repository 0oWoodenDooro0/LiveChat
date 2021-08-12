package com.example.livechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import user

class LoginActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register_text.setOnClickListener(this)
        login_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            register_text -> startActivity(Intent(this, RegisterActivity::class.java))
            login_btn -> login()
        }
    }

    private fun login(){

        var email = editext_email.text.toString()
        var password = editext_password.text.toString()

        if(email.isEmpty()){
            editext_email.setError("請輸入電子信箱")
            editext_email.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editext_email.setError("請輸入有效的電子信箱")
            editext_email.requestFocus()
            return
        }

        if(password.isEmpty()){
            editext_password.setError("請輸入密碼")
            editext_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "登入失敗，再試一次", Toast.LENGTH_LONG).show()
            }
        }
    }

}