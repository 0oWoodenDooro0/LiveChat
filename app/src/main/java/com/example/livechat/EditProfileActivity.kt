package com.example.livechat

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
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
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        val items = listOf("男","女")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        editprofile_sex.setAdapter(adapter)

        userreference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sex = ""
                var birthday = ""
                var name = ""

                if(snapshot.child("Name").value != null){
                    name = snapshot.child("Name").value.toString()
                }

                if(snapshot.child("Sex").value != null){
                    sex = snapshot.child("Sex").value.toString()
                }

                if(snapshot.child("Birthday").value != null){
                    birthday = snapshot.child("Birthday").value.toString()
                }

                editprofile_name.setText(name)
                editprofile_sex.setText(sex, false)
                editprofile_birthday.setText(birthday)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun check(){
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.child("Name")?.setValue(editprofile_name.text.toString())
        userreference?.child("Sex")?.setValue(editprofile_sex.text.toString())
        userreference?.child("Birthday")?.setValue(editprofile_birthday.text.toString())
    }

}
