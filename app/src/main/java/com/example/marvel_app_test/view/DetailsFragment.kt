package com.example.marvel_app_test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.marvel_app_test.R
import com.example.marvel_app_test.databinding.DetailsFragmentBinding
import com.example.marvel_app_test.model.Characters

class DetailsFragment (private val characters: Characters) : Fragment(R.layout.details_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCharacterDetails(view)
    }

    private fun loadCharacterDetails(view: View){
        val binding = DetailsFragmentBinding.bind(view)
        binding.includedLayoutDetails.tvNameCharacterDetails.text = characters.name
        binding.includedLayoutDetails.tvDescriptionDetails.text = characters.description

        Glide.with(view)
            .load(characters.thumbnail.concatImage())
            .into(binding.includedLayoutDetails.ivCharacterDetails)

        binding.includedLayoutDetails.ivIconBackDetails.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}