package com.example.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.custom_view.databinding.CardViewBinding

class CardViewLayout @JvmOverloads constructor(context: Context,
                                               attributes: AttributeSet,
                                               defStyleAttributes: Int = 0) : ConstraintLayout(context, attributes, defStyleAttributes) {

    private var name: String? = null
    private var image: String? = null
    private val binding = CardViewBinding.inflate(LayoutInflater.from(context),this, true)

    init {
        setLayout(attributes)
    }

    private fun setLayout(attribute: AttributeSet?){
        attribute?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CardViewLayout)

            val nameResId = attributes.getResourceId(R.styleable.CardViewLayout_name_character,0)
            if (nameResId != 0){
                name = context.getString(nameResId)
            }

            val imageResId = attributes.getResourceId(R.styleable.CardViewLayout_image_character,0)
            if (imageResId != 0){
                image = context.getString(imageResId)
            }

            attributes.recycle()
        }
    }

    fun setText(text : String){
        binding.nameCharacter.text = text
    }

    fun setImage(image : String){
        Glide.with(this)
            .load(image)
            .into(binding.ivFeaturedCharacter)
    }
}