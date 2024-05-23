package com.example.appdrivinglience.feature.history_examination

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.appdrivinglience.core.FileSharePreference
import com.example.appdrivinglience.database.dao.HistoryDao
import com.example.appdrivinglience.database.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryExaminationViewModel @Inject constructor(
    private val historyDao: HistoryDao,
    private val fileSharePreference: FileSharePreference
): ViewModel() {

    private val _historyState = mutableStateOf<HistoryState>(HistoryState.Loading)
    val historyState : State<HistoryState> = _historyState

    fun getDataHistory(key: String): Map<Int, Int> {
        val folder = fileSharePreference.getCategoryLicense()
        val historyList =  historyDao.getAllHistoryList(key)

       return  historyList.mapIndexed { index, history -> index to history.answerExam }.toMap()
    }

}
sealed class HistoryState(){
    data object Loading : HistoryState()
    data class Success(val data: List<History>) : HistoryState()
    data class Error (val error : String?): HistoryState()
}