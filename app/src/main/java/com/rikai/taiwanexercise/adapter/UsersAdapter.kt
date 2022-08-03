package com.rikai.taiwanexercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rikai.taiwanexercise.R
import com.rikai.taiwanexercise.models.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){
    var users =  listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avatarImg: ImageView = itemView.findViewById(R.id.avatar_img)
        val loginTv: TextView = itemView.findViewById(R.id.login_tv)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.activity_users_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: User) {
            Glide.with(itemView.context).load(item.avatarUrl).into(avatarImg)
            loginTv.text = item.login
        }
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }
}