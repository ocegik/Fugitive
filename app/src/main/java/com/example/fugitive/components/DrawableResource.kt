package com.example.fugitive.components

import com.example.fugitive.R

fun getDrawableResourceId(imageName: String): Int {
    return when (imageName) {
        "lion" -> R.drawable.lion
        "owl" -> R.drawable.owl
        "sale" -> R.drawable.sale
        "koala" -> R.drawable.koala
        "zebra" -> R.drawable.zebra
        "dog" -> R.drawable.dog
        "camel" -> R.drawable.camel
        "hippo" -> R.drawable.hippo
        else -> R.drawable.user_placeholder
    }
}