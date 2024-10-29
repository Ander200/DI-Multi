package com.example.myfirstapplication.models

data class Artwork(
    val name: String,
    val title: String,
    val description: String,
    val creationDate: String,
    val style: ArtworkStyle,  // Enum que define el estilo de arte
    val imageResId: Int // ID del recurso de la imagen (JPG)
)

enum class ArtworkStyle {
    WATERCOLOUR,  // Acuarela
    DIGITAL,      // Arte digital
    INK           // Tinta
}