package com.example.fugitive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.fugitive.data.remote.api.dictionary.DictionaryResponse
import com.example.fugitive.data.remote.api.dictionary.DictionaryRetrofitClient

class DictionaryViewModel : ViewModel() {

    private val _result = mutableStateOf<List<DictionaryResponse>?>(null)
    val result: State<List<DictionaryResponse>?> = _result

    fun search(word: String) {
        viewModelScope.launch {
            try {
                val response = DictionaryRetrofitClient.api.getMeaning(word)
                if (response.isSuccessful) {
                    _result.value = response.body()
                } else {
                    _result.value = null
                }
            } catch (e: Exception) {
                _result.value = null
            }
        }
    }
}
