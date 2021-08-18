package com.example.livechat

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_friends.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue


class FriendsFragment : Fragment() {

    var database : DatabaseReference? = null
    val itemlist = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {

        database = FirebaseDatabase.getInstance().getReference().child("profile")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater!!.inflate(R.layout.fragment_friends, container, false)

        database?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children) {
                    var item = snapshot.getValue<Item>()
                    if (item != null) {
                        itemlist.add(item)

                    }
                }
                createitem(itemlist, view.context)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return view
    }

    private fun createitem(items : List<Item>, context : Context){
        friend_list.layoutManager = LinearLayoutManager(context)
        friend_list.adapter = RecyclerViewAdapter(items,context)
    }

}