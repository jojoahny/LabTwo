package com.example.labtwo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(var list: ImagesResponse?,val listener:myClickListener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val imgView: ImageView = itemview.findViewById(R.id.imgView)
        init {
            itemview.setOnClickListener{
                val position=adapterPosition
                listener.onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycle, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(list!![position].url).placeholder(R.drawable.loading).into(holder.imgView)
    }
    interface myClickListener{
        fun onClick(position: Int)
    }

}