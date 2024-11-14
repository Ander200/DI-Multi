package com.example.myfirstapplication.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R

@Composable
fun InfoScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        item {
            Headers()
            Menu()
            Foot()
        }
    }
}

@Composable
fun Option(name: String, image: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .border(1.dp, color = Color.Black)
            .height(60.dp)
    ) {
        //añadir imagen
        Image(
            painter = painterResource(id = image),
            contentDescription = "accesibilidad",
            modifier = Modifier
                .padding(start = 5.dp)
                .size(40.dp)
        )
        Text(
            text = name,
            color = Color.White,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(start = 5.dp)
        )
    }
}

@Composable
fun Menu() {
    Column {
        Option("Archivos", image = R.drawable.baseline_drive_file_move_24)
        Option("Jugar", image = R.drawable.baseline_play_circle_filled_24)
        Option("Configuracion", image = R.drawable.baseline_settings_24)
    }
}

@Composable
fun Headers() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .border(1.dp, color = Color.Black)
            .padding(10.dp)
            .padding(vertical = 40.dp)
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "default",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(3.dp, Color.Gray),
                    CircleShape
                )
        )
        Text(
            text = "Ander",
            color = Color.White,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Soy un diseñador y esto es un ejemplo que " +
                    "estoy poniendo de texto, Hola mundo",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Foot() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .border(1.dp, color = Color.Black)
            .padding(10.dp)
            .padding(vertical = 30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_group_add_24),
            contentDescription = "accesibilidad",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(3.dp, Color.Green),
                    CircleShape
                )
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_group_24),
            contentDescription = "accesibilidad",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(3.dp, Color.Blue),
                    CircleShape
                )
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_group_remove_24),
            contentDescription = "accesibilidad",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(3.dp, Color.Red),
                    CircleShape
                )
        )
    }
}