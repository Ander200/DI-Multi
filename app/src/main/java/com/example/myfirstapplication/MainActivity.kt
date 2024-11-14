package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapplication.components.BottomNavBar
import com.example.myfirstapplication.components.GalleryTopBar
import com.example.myfirstapplication.components.LikesBtn
import com.example.myfirstapplication.components.ToolBar
import com.example.myfirstapplication.viewModels.ContadorViewModel
import com.example.myfirstapplication.viewModels.GalleryViewModel
import com.example.myfirstapplication.views.GalleryScreen
import com.example.myfirstapplication.views.HomeScreen
import com.example.myfirstapplication.views.InfoScreen
import com.example.myfirstapplication.views.SettingsScreen

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
    val contador = ContadorViewModel()
    val navController = rememberNavController()
    val galleryViewModel = GalleryViewModel()
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
            composable("home") { HomeScreen(innerPadding, contador) }
            composable("info") { InfoScreen(innerPadding) }
            composable("gallery") { GalleryScreen(innerPadding, galleryViewModel) }
            composable("settings") { SettingsScreen(innerPadding) }
        }
    }
}
