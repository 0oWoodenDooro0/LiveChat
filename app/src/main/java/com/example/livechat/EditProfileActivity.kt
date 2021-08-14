package com.example.livechat

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import user
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : AppCompatActivity(){

    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    private var storage : StorageReference? = null
    private var firebase : FirebaseDatabase? = null
    private val dateformat = SimpleDateFormat("YYYY/MM/dd", Locale.getDefault())
    var image_pick = registerForActivityResult(ActivityResultContracts.GetContent()){
        Picasso.get().load(it)
            .resize(1024, 1024)
            .onlyScaleDown()
            .centerCrop()
            .into(editprofile_image)
        imageuri = it
    }
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            image_pick.launch("image/*")
        }else{
            Toast.makeText(this, "你拒絕了權限無法開啟檔案",Toast.LENGTH_SHORT).show()
        }
    }
    var imageuri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase?.reference!!.child("profile")
        storage = FirebaseStorage.getInstance().getReference().child("profile")

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
                    true
                }
                else ->false
            }
        }
        image_btn.setOnClickListener{
            PickImage()
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
            editprofile_name.setText(name)
        }

        if(user.sex != "null"){
            sex = user.sex
            editprofile_sex.setText(sex, false)
        }

        if(user.birthday != "null"){
            birthday = user.birthday
            editprofile_birthday.setText(birthday)
        }

        if(user.imageurl != null){
//            storage?.child(user.imageurl)?.downloadUrl?.addOnSuccessListener {
//                Picasso.get()
//                    .load(it)
//                    .resize(2000, 2000)
//                    .onlyScaleDown()
//                    .centerCrop()
//                    .into(editprofile_image)
//            }?.addOnFailureListener{
//                Toast.makeText(this, "讀取失敗", Toast.LENGTH_SHORT).show()
//            }
//            Picasso.get()
//                    .load(user.imageurl)
//                    .resize(2000, 2000)
//                    .onlyScaleDown()
//                    .centerCrop()
//                    .into(editprofile_image)
        }
    }

    private fun check(){
        val currentuser = auth.currentUser
        val database =  databaseReference?.child(currentuser?.uid!!)

        database?.child("Name")?.setValue(editprofile_name.text.toString())
        database?.child("Sex")?.setValue(editprofile_sex.text.toString())
        database?.child("Birthday")?.setValue(editprofile_birthday.text.toString())
        if(imageuri != null){
            var image = storage?.child(user.uid + ".png")
            image?.putFile(imageuri!!)?.addOnSuccessListener {
                Toast.makeText(this, "成功" ,Toast.LENGTH_SHORT).show()
                finish()
            }?.addOnFailureListener {
                Toast.makeText(this, "上傳失敗", Toast.LENGTH_SHORT).show()
                finish()
            }
            storage?.child(user.uid + ".png")?.downloadUrl?.addOnSuccessListener {
//                user.imageurl = it
//                user.imageurl = user.uid + ".png"
            }
        }

    }

    private fun PickImage(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                image_pick.launch("image/*")
            }
            else -> {
                permission.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


}
