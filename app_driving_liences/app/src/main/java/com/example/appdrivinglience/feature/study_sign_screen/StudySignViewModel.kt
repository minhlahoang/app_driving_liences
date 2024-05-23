package com.example.appdrivinglience.feature.study_sign_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.appdrivinglience.database.model.SignModel

class StudySignViewModel : ViewModel() {
    var signState = mutableStateOf<SignState>(SignState.None)

    fun setSignModel(data: SignModel){
        signState.value = SignState.DataSign(data)
    }
}
sealed class SignState(){
    data object None : SignState()
    data class DataSign(val data: SignModel): SignState()
}