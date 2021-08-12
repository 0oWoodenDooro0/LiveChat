package com.example.livechat

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import user
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null
    private val dateformat = SimpleDateFormat("YYYY/MM/dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        load()

        editprofile_toolbar.setNavigationOnClickListener {
            finish()
        }

        val datepicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("生日")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datepicker.addOnPositiveButtonClickListener {
            var dateselected = dateformat.format(it)
            editprofile_birthday.setText(dateselected)
        }

        editprofile_birthday.setOnClickListener{
            datepicker.show(supportFragmentManager, "Date Picker")
        }

        editprofile_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.check ->{
                    check()
                    finish()
                    true
                }
                else ->false
            }
        }

    }

    private fun load(){

        val items = listOf("男","女")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        editprofile_sex.setAdapter(adapter)

        var sex = ""
        var birthday = ""
        var name = ""

        if(user.name != "null"){
            name = user.name
        }

        if(user.sex != "null"){
            sex = user.sex
        }

        if(user.birthday != "null"){
            birthday = user.birthday
        }

        editprofile_name.setText(name)
        editprofile_sex.setText(sex, false)
        editprofile_birthday.setText(birthday)

    }

    private fun check(){
        val currentUser = auth.currentUser
        val currentUserDb =  databaseReference?.child(currentUser?.uid!!)

        currentUserDb?.child("Name")?.setValue(editprofile_name.text.toString())
        currentUserDb?.child("Sex")?.setValue(editprofile_sex.text.toString())
        currentUserDb?.child("Birthday")?.setValue(editprofile_birthday.text.toString())
    }

}
