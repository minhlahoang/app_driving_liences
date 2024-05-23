package com.example.appdrivinglience.feature.trick_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.database.dao.TrickDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TrickViewModel @Inject constructor(
    private val trickDao: TrickDao
): ViewModel(){
    private val _trickState = mutableStateOf<TrickState>(TrickState.Loading)
    val trickState : State<TrickState> = _trickState
    fun getAllTrick() {
        _trickState.value = TrickState.Loading
        viewModelScope.launch {
            runCatching {
                _trickState.value = TrickState.Success(trickDao.getAllTrick())
            }.onFailure {
                _trickState.value = TrickState.Error(it.message)
            }
        }
    }
}
sealed class TrickState {
    data object Loading : TrickState()
    data class Success<out T>(val trickList : T): TrickState()
    data class Error (val error : String?): TrickState()
}