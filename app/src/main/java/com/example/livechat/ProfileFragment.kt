package com.example.livechat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import user

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private var storage : StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance().getReference().child("profile")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)

        view.logout_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        view.edit_btn.setOnClickListener{
            startActivity(Intent(activity,EditProfileActivity::class.java))
        }

        view?.textview_name?.text = "暱稱: " + user.name
        if(user.sex == "null"){
            view?.textview_sex?.text = "性別: 未公開"
        }
        else{
            view?.textview_sex?.text = "性別: " + user.sex
        }
        if(user.birthday == "null"){
            view?.textview_birthday?.text = "生日: 未公開"
        }
        else{
            view?.textview_birthday?.text = "生日: " + user.birthday
        }
        if(user.imageurl != null){
//            storage?.child(user.imageurl)?.downloadUrl?.addOnSuccessListener {
//                Picasso.get()
//                    .load(it)
//                    .resize(2000, 2000)
//                    .onlyScaleDown()
//                    .centerCrop()
//                    .into(image)
//            }?.addOnFailureListener{
//                Toast.makeText(activity, "讀取失敗", Toast.LENGTH_SHORT).show()
//            }
//            Picasso.get()
//                    .load(user.imageurl)
//                    .resize(2000, 2000)
//                    .onlyScaleDown()
//                    .centerCrop()
//                    .into(image)
        }
        return view
    }

}