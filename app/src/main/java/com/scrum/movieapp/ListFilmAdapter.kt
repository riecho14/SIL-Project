package com.scrum.movieapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListFilmAdapter(private val listFilm: ArrayList<Film>) :
    RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_film, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = listFilm[position]
        Glide.with(holder.itemView.context).load(currentItem.image).into(holder.image)
        holder.name.text = currentItem.title
        holder.description.text = currentItem.description
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_film", listFilm[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listFilm.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.film_name)
        val description: TextView = itemView.findViewById(R.id.film_description)
        val image: ImageView = itemView.findViewById(R.id.film_photo)
    }
}