package com.example.myfirstapplication.components

import android.widget.Toast
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun LikesBtn() {
    val context = LocalContext.current
    FloatingActionButton(onClick = {
        Toast.makeText(context, "Me gusta", Toast.LENGTH_SHORT).show()
    }) {
        Text(
            text = "+",
            fontSize = 40.sp
        )
    }
}