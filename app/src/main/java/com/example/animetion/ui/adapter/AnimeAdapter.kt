package com.example.animetion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animetion.R
import com.example.animetion.databinding.ItemAnimeBinding
import com.example.animetion.logic.data.Anime
import com.squareup.picasso.Picasso

class AnimeAdapter (
    private var items: List<Anime>,
) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>(){

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding : ItemAnimeBinding = ItemAnimeBinding.bind(view)

        fun render(item : Anime
        ){
            println("Recibiendo a ${item.name}")
            Picasso.get().load(item.imageUrl).into(binding.imgAnime)
            binding.tvAnimeNombre.text = item.name
            binding.tvAnimeType.text = "Tipo: "+item.type
            binding.tvAnimeEpisodes.text = "Ep: "+item.episodes.toString()


            itemView.setOnClickListener{
                //fnClick(item)
//                Snackbar.make(binding.imgMarvel,
//                    item.name,
//                    Snackbar.LENGTH_SHORT)
//                    .show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeAdapter.AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AnimeViewHolder(
            inflater.inflate(
                R.layout.item_anime,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: AnimeAdapter.AnimeViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateListItem(newItems: List<Anime>){
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newItems: List<Anime>){
        this.items = newItems
        notifyDataSetChanged()
    }

}