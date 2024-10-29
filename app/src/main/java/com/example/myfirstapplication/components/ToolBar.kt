package com.example.myfirstapplication.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "About Me",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .clickable {
                            val webIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://www.google.com")
                            }

                            // Iniciar el intent para abrir el navegador
                            context.startActivity(webIntent)
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = "accesibilidad",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(40.dp)
                        .clickable(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain" // Tipo de dato que se va a compartir
                                putExtra(Intent.EXTRA_SUBJECT, "Mira mi sitio web")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "https://www.misitio.com"
                                ) // El contenido a compartir
                            }

                            // Crear un chooser para mostrar las opciones disponibles de aplicaciones para compartir
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Compartir con"
                                )
                            )
                        })
                )

            }
        }
    )
}