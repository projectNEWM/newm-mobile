package com.projectnewm.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GenreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is genre Fragment"
    }
    val text: LiveData<String> = _text
}