package com.example.marvel_app_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel_app_test.R
import com.example.marvel_app_test.databinding.ItemCharacterListBinding
import com.example.marvel_app_test.model.Characters

class AdapterCharacterList(private val onClick : (Characters) -> Unit) : RecyclerView.Adapter<CharacterListViewHolder>() {

    private var listOfCharacters = mutableListOf<Characters?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_character_list, parent, false)
            .apply {
                return CharacterListViewHolder(this)
            }
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        listOfCharacters[position]?.apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int = listOfCharacters.size

    fun refreshList(characters: List<Characters?>, clearList: Boolean = false) {
        if (clearList) {
            listOfCharacters.clear()
        }
        listOfCharacters.addAll(characters)
        notifyDataSetChanged()
    }
}

class CharacterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemCharacterListBinding.bind(itemView)

    fun bind(characters: Characters) {
        binding.nameCharacterList.text = characters.name

        Glide.with(itemView)
            .load(characters.thumbnail.concatImage())
            .into(binding.ivFeaturedCharacterList)
    }
}