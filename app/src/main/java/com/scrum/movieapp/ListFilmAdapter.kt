package com.scrum.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scrum.movieapp.databinding.ItemRowFilmBinding

class ListFilmAdapter(private val listFilm: ArrayList<Film>): RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemRowFilmBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val binding = ItemRowFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, image, photo) = listFilm[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.binding.filmPhoto)
        holder.binding.filmName.text = name
        holder.binding.filmDescription.text = image
        holder.itemView.setOnClickListener{
//            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
//            intentDetail.putExtra("key_film", listFilm[holder.adapterPosition])
//            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listFilm.size
}