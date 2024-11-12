package com.example.myfirstapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstapplication.viewModels.ContadorViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    contadorViewModel: ContadorViewModel
) {
    val contador by contadorViewModel.contador.observeAsState(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(innerPadding)
    ) {
        item {
            Text(
                text = "Home",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Button(
                    onClick = {
                        contadorViewModel.decrement()
                    }
                )  {
                    Text(
                        text = "-",
                        fontSize = 20.sp,
                    )
                }
                Text(
                    text = "$contador",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                Button(
                    onClick = {
                        contadorViewModel.increment()
                    }
                )  {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }

}