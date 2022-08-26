package com.hcl.retrofit.coroutines.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcl.retrofit.coroutines.R
import com.hcl.retrofit.coroutines.data.model.User
import com.hcl.retrofit.coroutines.ui.main.adapter.MainAdapter.DataViewHolder
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.apply {
                textViewUserName.text = user.name
                textViewUserEmail.text = user.email
                /*  Glide.with(imageViewAvatar.context)
                      .load(user.avatar)
                      .into(imageViewAvatar)*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
}