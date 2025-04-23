package com.example.myfirstapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfirstapplication.viewModels.AddressFormatViewModel
import com.example.myfirstapplication.viewModels.ContadorViewModel
import com.example.myfirstapplication.viewModels.LoaderViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    contadorViewModel: ContadorViewModel,
    loaderViewModel: LoaderViewModel,
    addressFormatViewModel: AddressFormatViewModel,
    navController: NavHostController
) {
    val contador by contadorViewModel.contador.observeAsState(0)
    val progress : Float by loaderViewModel.progress.observeAsState(0f)
    val selectedFormat by remember { addressFormatViewModel::selectedFormat }
    val formattedAddress = addressFormatViewModel.getFormatAddress()

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
                    color = Color.White,
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                CircularProgressIndicator(
                    progress = {progress}
                )

                LinearProgressIndicator(
                    progress = {progress}
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Button(
                    onClick = {
                        loaderViewModel.startProgress()
                    }) {
                    Text("Iniciar")
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = formattedAddress,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Opciones de Formato usando RadioButton
                FormatOption(
                    label = "Original",
                    selected = selectedFormat == AddressFormatViewModel.FormatType.ORIGINAL,
                    onSelect = { addressFormatViewModel.onFormatSelected(AddressFormatViewModel.FormatType.ORIGINAL) }
                )
                FormatOption(
                    label = "Una Linea",
                    selected = selectedFormat == AddressFormatViewModel.FormatType.ONE_LINE,
                    onSelect = { addressFormatViewModel.onFormatSelected(AddressFormatViewModel.FormatType.ONE_LINE) }
                )
                FormatOption(
                    label = "MultiLinea",
                    selected = selectedFormat == AddressFormatViewModel.FormatType.MULTILINE,
                    onSelect = { addressFormatViewModel.onFormatSelected(AddressFormatViewModel.FormatType.MULTILINE) }
                )
            }

            Button(
                onClick = {
                    navController.navigate("todo") // Navigate to the To-Do screen
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to To-Do List")
            }

            Button(
                onClick = {
                    navController.navigate("colorPicker")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to Color Picker")
            }
        }
    }

}

@Composable
fun FormatOption(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}