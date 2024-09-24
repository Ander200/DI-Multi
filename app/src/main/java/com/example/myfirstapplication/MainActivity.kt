package com.example.myfirstapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceActivity.Header
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewApp()
        }
    }
}

@Preview
@Composable
fun ViewApp() {
    Scaffold (
        topBar =  {ToolBar()},
        content = {Content()},
        floatingActionButton = {LikesBtn()},
        floatingActionButtonPosition = FabPosition.End,
        bottomBar =  { BottomBar() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
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
                                putExtra(Intent.EXTRA_TEXT, "https://www.github.com") // El contenido a compartir
                            }

                            // Crear un chooser para mostrar las opciones disponibles de aplicaciones para compartir
                            context.startActivity(Intent.createChooser(shareIntent, "Compartir con"))
                        })
                )

            }
        }
    )
}

//@Preview
@Composable
fun LikesBtn() {
    val context = LocalContext.current
    FloatingActionButton(onClick = {
        Toast.makeText(context,"Me gusta",Toast.LENGTH_SHORT).show()
    }){
        Text(
            text = "+",
            fontSize = 40.sp
        )
    }
}

//@Preview
@Composable
fun BottomBar() {
    val context = LocalContext.current
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_mail_24),
            contentDescription = "accesibilidad",
            modifier = Modifier
                .padding(end = 5.dp)
                .size(40.dp)
                .clickable(onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // Solo abre aplicaciones de correo
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("ejemplo@correo.com")) // Correo destinatario
                        putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo") // Asunto del correo
                    }

                    // Iniciar el intent
                    if (emailIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(emailIntent)
                    }
                })
        )
    }
}

//@Preview
@Composable
fun Content() {
    Column {
        Headers()
        Menu()
        Foot()
    }
}

@Composable
fun Option(name: String, image: Int) {
    Row (
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
            text = "$name",
            color = Color.White,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(start = 5.dp)
        )
    }
}

//@Preview
@Composable
fun OptionPreview() {
    Option(name = "Test", image = R.drawable.baseline_accessibility_24)
}

@Composable
fun Menu() {
    Column {
        Option("Archivos", image = R.drawable.baseline_drive_file_move_24)
        Option("Jugar", image = R.drawable.baseline_play_circle_filled_24)
        Option("Configuracion", image = R.drawable.baseline_settings_24)
    }
}

//@Preview(name = "columna")
@Composable
fun MenuPreview() {
    Menu()
}

@Composable
fun Headers() {
    Column (
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
                .size(140.dp)
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

//@Preview
@Composable
fun HeaderPreview() {
    Headers()
}

@Composable
fun Foot() {
    Row (
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

//@Preview
@Composable
fun FootPreview() {
    Foot()
}
