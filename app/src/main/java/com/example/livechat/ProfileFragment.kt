package com.example.livechat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    var database : DatabaseReference? = null
    private var storage : StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference().child("profile")
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

//        database?.child(user.uid)?.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                user.name = snapshot.child("name").value.toString()
//                user.sex = snapshot.child("sex").value.toString()
//                user.birthday = snapshot.child("birthday").value.toString()
//                user.imageurl = snapshot.child("imageurl").value.toString()
//                load(view.image)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

        load(view)

        return view
    }

    private fun load(view: View){
        view?.textview_name?.text = "??????: " + user.name
        if(user.sex == ""){
            view?.textview_sex?.text = "??????: ?????????"
        }
        else{
            view?.textview_sex?.text = "??????: " + user.sex
        }
        if(user.birthday == ""){
            view?.textview_birthday?.text = "??????: ?????????"
        }
        else{
            view?.textview_birthday?.text = "??????: " + user.birthday
        }
        if(user.imageurl != ""){
            Picasso.get()
                .load(user.imageurl)
                .resize(2000, 2000)
                .onlyScaleDown()
                .centerCrop()
                .into(view.image)
        }
    }

}