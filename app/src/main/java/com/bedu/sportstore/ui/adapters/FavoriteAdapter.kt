package com.bedu.sportstore.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.databinding.ListItemBinding
import com.bedu.sportstore.db.Favorite


class FavoriteAdapter (val favoritelistfa:List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoriteHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.render(favoritelistfa[position])
    }

    override fun getItemCount(): Int = favoritelistfa.size

    class FavoriteHolder(val view: View):RecyclerView.ViewHolder(view){
        val binding = ListItemBinding.bind(view)

        fun render(favoritelistfa: Favorite){
            binding.tvFolioFavorite.text = favoritelistfa.folio
            binding.tvPriceFavorite.text = favoritelistfa.price
            binding.tvDateFavorite.text = favoritelistfa.date
            binding.tvStatusFavorite.text = favoritelistfa.status
        }
    }
}