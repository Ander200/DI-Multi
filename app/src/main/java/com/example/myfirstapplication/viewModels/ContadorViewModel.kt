package com.example.myfirstapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContadorViewModel : ViewModel() {
    private var _contador = MutableLiveData(0)
    val contador: LiveData<Int> = _contador

    fun increment() {
        _contador.value = (_contador.value ?: 0) + 1
    }

    fun decrement() {
        _contador.value = (_contador.value ?: 0) - 1
    }
}