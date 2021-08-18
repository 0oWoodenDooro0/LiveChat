package com.example.livechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val items : List<Item>, var context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_friends_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items[position].imageurl != ""){
            Picasso.get()
                .load(items[position].imageurl)
                .resize(2000, 2000)
                .onlyScaleDown()
                .centerCrop()
                .into(holder.image)
        }
        holder.name.text = items[position].name
    }

    class ViewHolder(val view: View) :  RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.friend_image)
        val name = view.findViewById<TextView>(R.id.friend_name)

    }
}