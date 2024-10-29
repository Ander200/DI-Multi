package com.example.myfirstapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.myfirstapplication.R
import com.example.myfirstapplication.components.ArtworkCard
import com.example.myfirstapplication.models.Artwork
import com.example.myfirstapplication.models.ArtworkStyle
import com.example.myfirstapplication.viewModels.GalleryViewModel

@Composable
fun GalleryScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    galleryViewModel: GalleryViewModel
) {
    GalleryContent(innerPadding, galleryViewModel.isSingleColumn, galleryViewModel.artworks.value)
}

fun sampleArtworks(): List<Artwork> {
    return listOf(
        Artwork(
            name = "Code",
            title = "Programadores Comentado el Codigo",
            description = "Programadores Comentado el Codigo",
            creationDate = "27/09/2024",
            style = ArtworkStyle.WATERCOLOUR,
            imageResId = R.drawable.img_galery_1
        ),
        Artwork(
            name = "Saul 1",
            title = "Saul Ink",
            description = "Saul Goodman",
            creationDate = "27/09/2024",
            style = ArtworkStyle.INK,
            imageResId = R.drawable.img_galery_2
        ),
        Artwork(
            name = "Universo 1",
            title = "Universo 1",
            description = "Universe",
            creationDate = "27/09/2024",
            style = ArtworkStyle.DIGITAL,
            imageResId = R.drawable.img_galery_3
        ),
        Artwork(
            name = "Saul 2",
            title = "Saul Watercolor",
            description = "Saul Goodman",
            creationDate = "27/09/2024",
            style = ArtworkStyle.WATERCOLOUR,
            imageResId = R.drawable.img_galery_2
        ),
        Artwork(
            name = "Universo 2",
            title = "Universo 2",
            description = "Universe",
            creationDate = "27/09/2024",
            style = ArtworkStyle.INK,
            imageResId = R.drawable.img_galery_3
        )
    )
}

@Composable
fun GalleryContent(
    paddingValues: PaddingValues,
    isSingleColumn: MutableState<Boolean>,
    artworks: List<Artwork>
) {
    LazyVerticalGrid(
        // Cambia entre una o dos columnas
        columns = if (isSingleColumn.value) GridCells.Fixed(1) else GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0.1f, 0.1f, 0.1f, 0.9f))
            .padding(paddingValues)
    ) {
        items(
            count = artworks.size, // Número de elementos en la lista
            key = { index -> artworks[index].name } // Clave única para cada obra
        ) { index ->
            val artwork = artworks[index] // Obtener la obra actual
            ArtworkCard(artwork)
        }
    }
}