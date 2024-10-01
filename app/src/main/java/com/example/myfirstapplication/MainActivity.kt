package com.example.myfirstapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewApp()
        }
    }
}

class GalleryViewModel : ViewModel() {
    // Controla si la galería se muestra en una o dos columnas
    var isSingleColumn = mutableStateOf(false)

    // Lista de obras de arte
    var artworks = mutableStateOf(sampleArtworks())
}

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

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = navController.currentBackStackEntry?.destination?.route == "info",
            onClick = {
                navController.navigate("info") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Face, contentDescription = "Gallery") },
            selected = navController.currentBackStackEntry?.destination?.route == "gallery",
            onClick = {
                navController.navigate("gallery") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = navController.currentBackStackEntry?.destination?.route == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Preview
@Composable
fun ViewApp() {
    val navController = rememberNavController()
    val galleryViewModel: GalleryViewModel = viewModel()
    val actualView = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        topBar = {
            when (actualView) {
                "gallery" -> GalleryTopBar(galleryViewModel.isSingleColumn)
                else -> ToolBar()
            }
        },
        floatingActionButton = { LikesBtn() },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { BottomNavBar(navController) },
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "info") {
            composable("home") { HomeScreen(innerPadding, navController) }
            composable("info") { InfoScreen(innerPadding, navController) }
            composable("gallery") { GalleryScreen(innerPadding, navController, galleryViewModel) }
            composable("settings") { SettingsScreen(innerPadding, navController) }
        }
    }
}

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Text(
            modifier = Modifier
                .padding(innerPadding),
            text = "Home",
            color = Color.White
        )
    }
}

@Composable
fun InfoScreen(innerPadding: PaddingValues, navController: NavController) {
    Content(innerPadding)
}

@Composable
fun GalleryScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    galleryViewModel: GalleryViewModel
) {
    GalleryContent(innerPadding, galleryViewModel.isSingleColumn, galleryViewModel.artworks.value)
}

@Composable
fun SettingsScreen(innerPadding: PaddingValues, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Text(
            modifier = Modifier
                .padding(innerPadding),
            text = "Settings",
            color = Color.White
        )
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryTopBar(singleColumn: MutableState<Boolean>) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Galeria",
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
                    painter = painterResource(
                        id = if (singleColumn.value) {
                            R.drawable.baseline_2_columns
                        } else {
                            R.drawable.baseline_1_column
                        }
                    ),
                    contentDescription = "accesibilidad",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(40.dp)
                        .clickable(onClick = {
                            singleColumn.value = !singleColumn.value
                        })
                )
            }
        }
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

@Composable
fun ArtworkCard(artwork: Artwork) {

    // Selección del ícono según el estilo de la obra
    val iconResId = when (artwork.style) {
        ArtworkStyle.WATERCOLOUR -> R.drawable.outline_palette_24
        ArtworkStyle.DIGITAL -> R.drawable.outline_video_stable_24
        ArtworkStyle.INK -> R.drawable.baseline_local_drink_24
    }

    Box(
        modifier = Modifier
            .padding(6.dp) // Padding alrededor de la tarjeta
            .shadow(8.dp, RoundedCornerShape(16.dp)) // Sombra con bordes redondeados
            .background(Color.White, RoundedCornerShape(16.dp)) // Fondo blanco y bordes redondeados
            .border(2.dp, Color.Yellow, RoundedCornerShape(16.dp)) // Borde fino
            .fillMaxWidth() // Ajustar el tamaño de la tarjeta
    ) {
        Column () {
            // Contenedor para superponer el icono sobre la imagen
            Box {
                // Imagen de la obra de arte
                Image(
                    painter = painterResource(id = artwork.imageResId),
                    contentDescription = "Imagen de la obra de arte",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth() // Llenar el ancho
                        .aspectRatio(1f) // Relación de aspecto 1:1 para que sea cuadrada
                        .clip(RoundedCornerShape(16.dp)) // Redondear la imagen
                )

                // Fondo para el icono (recuadro gris oscuro)
                Box(
                    modifier = Modifier
                        .size(36.dp) // Tamaño del recuadro
                        .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp)) // Color gris oscuro con bordes redondeados
                        .align(Alignment.TopEnd) // Alinear en la esquina superior derecha
                        .padding(8.dp) // Padding para ajustar la posición
                ) {

                    // Icono superpuesto
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = "Icono del estilo de la obra de arte",
                        modifier = Modifier
                            .size(24.dp) // Tamaño del icono
                            .align(Alignment.Center), // Centrar el icono dentro del recuadro
                        tint = Color.White // Color del icono
                    )
                }
            }

            // Fondo para el titulo y la fecha
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Llenar el ancho
                    .background(Color(0xFF2C2C2C)) // Color gris oscuro con bordes redondeados
                    .padding(8.dp) // Padding para ajustar la posición
            ) {
                // Título de la obra de arte
                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.bodyLarge, // Usando la fuente EB Garamond definida en el tema
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp), // Padding alrededor del título
                    color = Color.White // Color del texto
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Llenar el ancho
                    .background(Color(0xFF2C2C2C)) // Color gris oscuro con bordes redondeados
                    .padding(2.dp) // Padding para ajustar la posición
            ) {
                // Fecha de la obra de arte
                Text(
                    text = artwork.creationDate,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(6.dp), // Padding alrededor de la fecha
                    color = Color.Gray // Color del texto
                )
            }
        }
    }
}

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

@Composable
fun Content(innerPadding: PaddingValues) {
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
            text = "$name",
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
