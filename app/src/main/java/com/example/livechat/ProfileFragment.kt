package com.example.livechat

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import user

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

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
        return view
    }

}