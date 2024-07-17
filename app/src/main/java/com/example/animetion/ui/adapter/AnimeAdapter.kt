package com.example.animetion.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.animetion.R
import com.example.animetion.databinding.ItemAnimeBinding
import com.example.animetion.logic.data.Anime
import com.google.android.material.internal.ViewUtils.dpToPx
import com.squareup.picasso.Picasso

class AnimeAdapter(
    private var items: List<Anime>,
    private val loopMode: Boolean = false,
    private val useScaleAnimation: Boolean = false,
    private val isLargeItem: Boolean = true // Agrega una bandera para indicar si el diseño es grande o pequeño
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    override fun getItemCount(): Int = if (loopMode) Int.MAX_VALUE else items.size

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemAnimeBinding = ItemAnimeBinding.bind(view)

        @SuppressLint("RestrictedApi")
        fun render(item: Anime, isLargeItem: Boolean) {
            println("Recibiendo a ${item.name}")
            Picasso.get().load(item.imageUrl).into(binding.imgAnime)
            binding.tvAnimeNombre.text = item.name
            binding.tvAnimeType.text = "Tipo: " + item.type
            binding.tvAnimeEpisodes.text = "Ep: " + item.episodes.toString()
            binding.tvAnimeGenero.text = "Generos: " + (item.genres?.get(0) ?: "None")

            // Ajustar el tamaño del CardView según el tipo de elemento
            if (isLargeItem) {
                val largeWidthDp = 200
                val largeHeightDp = 390
                val largeWidthPx = dpToPx(itemView.context, largeWidthDp)
                val largeHeightPx = dpToPx(itemView.context, largeHeightDp)
                binding.cardView.layoutParams.width = largeWidthPx.toInt()
                binding.cardView.layoutParams.height = largeHeightPx.toInt()
            } else {
                // Ajusta aquí el tamaño deseado para el diseño más pequeño
                val smallWidthDp = 170
                val smallHeightDp = 390
                val smallWidthPx = dpToPx(itemView.context, smallWidthDp)
                val smallHeightPx = dpToPx(itemView.context, smallHeightDp)
                binding.cardView.layoutParams.width = smallWidthPx.toInt()
                binding.cardView.layoutParams.height = smallHeightPx.toInt()
            }

            itemView.setOnClickListener {
                //fnClick(item)
                // Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeAdapter.AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AnimeViewHolder(
            inflater.inflate(
                R.layout.item_anime,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AnimeAdapter.AnimeViewHolder, position: Int) {
        val realPosition = if (loopMode) position % items.size else position
        holder.render(items[realPosition], isLargeItem)

        // Aplicar la animación de escala solo si useScaleAnimation es verdadero
        if (useScaleAnimation) {
            applyScaleAnimation(holder.itemView)
        }
    }

    fun updateListItem(newItems: List<Anime>) {
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newItems: List<Anime>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    // Función de extensión para convertir dp a píxeles
    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    private fun applyScaleAnimation(view: View) {
        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = 350
        view.startAnimation(scaleAnimation)
    }
}