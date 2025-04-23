package com.example.myfirstapplication.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Format

class AddressFormatViewModel : ViewModel() {
    private val _address = MutableLiveData<String>()

    init {
        _address.value = "1234 Calle falsa, piso 0, ciudad, Pais Vasco, 12345"
    }

    // Estado para guardar el formato seleccionado
    var selectedFormat by mutableStateOf(FormatType.ORIGINAL)
        private set

    // Funcion para cambiar el formato
    fun onFormatSelected(format: FormatType) {
        selectedFormat = format
    }

    enum class FormatType {
        ORIGINAL, ONE_LINE, MULTILINE
    }

    // Funcion para simular el progreso
    fun getFormatAddress(): String {
        val address = _address.value ?: return ""
        return when (selectedFormat) {
            FormatType.ORIGINAL -> address
            FormatType.ONE_LINE -> address.replace(","," -")
            FormatType.MULTILINE -> address.replace(",", "\n")
        }
    }
}