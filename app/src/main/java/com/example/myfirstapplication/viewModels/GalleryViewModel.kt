package com.example.myfirstapplication.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myfirstapplication.views.sampleArtworks

class GalleryViewModel : ViewModel() {
    // Controla si la galería se muestra en una o dos columnas
    var isSingleColumn = mutableStateOf(false)

    // Lista de obras de arte
    var artworks = mutableStateOf(sampleArtworks())
}