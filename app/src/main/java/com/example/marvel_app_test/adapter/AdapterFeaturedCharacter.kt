package com.example.marvel_app_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app_test.R
import com.example.marvel_app_test.databinding.ItemFeaturedCharacterBinding
import com.example.marvel_app_test.model.Characters

class AdapterFeaturedCharacter(private val onClick: (Characters) -> Unit) : RecyclerView.Adapter<FeatureCharacterViewHolder>() {

    private var listOfFeaturedCharacters = mutableListOf<Characters?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureCharacterViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_featured_character, parent, false).apply {
            return FeatureCharacterViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: FeatureCharacterViewHolder, position: Int) {
        listOfFeaturedCharacters[position]?.apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfFeaturedCharacters.size

    fun refresh(characters: List<Characters?>) {
        listOfFeaturedCharacters.clear()
        listOfFeaturedCharacters.addAll(characters)
        notifyDataSetChanged()
    }
}

class FeatureCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemFeaturedCharacterBinding.bind(itemView)

    fun bind(characters: Characters) {
        binding.customCardView.apply {
            setText(characters.name)
            setImage(characters.thumbnail.concatImage())
        }
    }
}