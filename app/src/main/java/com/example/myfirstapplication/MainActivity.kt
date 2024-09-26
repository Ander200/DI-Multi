package com.example.myfirstapplication

import android.content.ClipData.Item
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
    val galleryViewModel : GalleryViewModel = viewModel()
    navController.currentBackStackEntryAsState().value?.destination
    Scaffold (
        topBar = {when(navController.currentDestination?.route) {
                    "gallery" -> GalleryTopBar(galleryViewModel.isSingleColumn.value)
                    else -> ToolBar()
                }}
        ,
        floatingActionButton = {LikesBtn()},
        floatingActionButtonPosition = FabPosition.End,
        bottomBar =  { BottomNavBar(navController) },
    ) {
        innerPadding ->
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
fun GalleryScreen(innerPadding: PaddingValues, navController: NavController, galleryViewModel : GalleryViewModel) {
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

fun sampleArtworks() : List<Artwork>{
    return listOf(
        Artwork(
            name = "test1",
            title = "title",
            description = "",
            creationDate = "",
            style = ArtworkStyle.WATERCOLOUR,
            imageResId = 1
        ),
        Artwork(
            name = "test2",
            title = "title2",
            description = "",
            creationDate = "",
            style = ArtworkStyle.INK,
            imageResId = 1
        ),
        Artwork(
            name = "test3",
            title = "title3",
            description = "",
            creationDate = "",
            style = ArtworkStyle.DIGITAL,
            imageResId = 1
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryTopBar(singleColumn: Boolean) {
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
                if (singleColumn == true) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_2_columns),
                        contentDescription = "accesibilidad",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(40.dp)
                            .clickable(onClick = {
                                singleColumn == false
                            })
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_1_column),
                        contentDescription = "accesibilidad",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(40.dp)
                            .clickable(onClick = {
                                singleColumn == true
                            })
                    )
                }

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
//            ArtworkCard(artwork)
        }
    }
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
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("ejemplo@correo.com")
                        ) // Correo destinatario
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
