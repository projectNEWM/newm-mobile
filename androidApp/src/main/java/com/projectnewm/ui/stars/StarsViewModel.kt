package com.projectnewm.ui.stars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StarsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is stars Fragment"
    }
    val text: LiveData<String> = _text
}