package com.example.appdrivinglience.feature.examination_test_screen

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.MainActivity
import com.example.appdrivinglience.core.FileSharePreference
import com.example.appdrivinglience.core.ReadFileUtils
import com.example.appdrivinglience.database.dao.ExaminationRandomDao
import com.example.appdrivinglience.database.dao.HistoryDao
import com.example.appdrivinglience.database.dao.QuestionDao
import com.example.appdrivinglience.database.model.ExaminationRandomModel
import com.example.appdrivinglience.database.model.History
import com.example.appdrivinglience.database.model.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class TestExaminationViewModel @Inject constructor(
    private val checkExaminationManager: CheckExaminationManager,
    private val readFileUtils: ReadFileUtils,
    private val fileSharePreference: FileSharePreference,
    private val historyDao: HistoryDao,
    private val questionDao: QuestionDao,
    private val examinationRandomDao: ExaminationRandomDao,

) : ViewModel() {

    private val _timerState = mutableStateOf("")
    val timeState : State<String> = _timerState
    private var timerCounter : CountDownTimer ?= null

    private val _isFinishedExamination = mutableStateOf(false)
    private val isFinishedExamination: State<Boolean> = _isFinishedExamination

    private val _scoreState = mutableIntStateOf(0)
    val scoreState : State<Int> = _scoreState


    private val _isPassState = mutableStateOf(true)
    val isPassState : State<Boolean> = _isPassState

    private val _idQuestionState = mutableIntStateOf(0)
    val idQuestionState : State<Int> = _idQuestionState

    val listQuestions = mutableListOf<Question>()
    val answerUserCurrentMap = mutableMapOf<Int, Int>()
    var numberExamination: String = "1"
    var pathFolder : String = ""

    private val _dataExamination = mutableStateOf<ExaminationState>(ExaminationState.Loading)
    val dataExamination : State<ExaminationState> = _dataExamination

    private val _isRandomState = mutableStateOf<Boolean>(false)
    val isRandomState : State<Boolean> = _isRandomState

    val answerUserMap = mutableMapOf<Long,Int>()

    val listExaminationRandom = mutableListOf<ExaminationRandomModel>()

    fun getNumberExamination(): Int{
        listExaminationRandom.clear()
        listExaminationRandom.addAll(examinationRandomDao.getAllExaminationRandom(getCategoryLicense()))
        println("listExaminationRandom = ${listExaminationRandom.size} ")
        val data = listExaminationRandom.distinctBy { it.idExam }.count()
        return readFileUtils.getNumberTest(fileSharePreference.getCategoryLicense()) + data
    }

    fun getNumberExaminationFile(): Int{
        return readFileUtils.getNumberTest(fileSharePreference.getCategoryLicense())
    }
    fun getCategoryLicense() : String {
        return fileSharePreference.getCategoryLicense()
    }

    fun totalNumberQuestion(): Int{
        return if (fileSharePreference.getCategoryLicense() == "A1" ||
            fileSharePreference.getCategoryLicense() == "A2" ||
            fileSharePreference.getCategoryLicense() == "A3" ||
            fileSharePreference.getCategoryLicense() == "A4" ){
            25
        }else if( fileSharePreference.getCategoryLicense() == "B1" ){
            30
        }
        else{
            35
        }
    }
    fun saveIsPass(key: String, data: Boolean){
        val folder  = "${fileSharePreference.getCategoryLicense()}_IS_PASS_$key"
        fileSharePreference.saveIsPass(folder,data)
    }

    fun getTimeForLicense(): Int {
        return if (fileSharePreference.getCategoryLicense() == "A1" ||
            fileSharePreference.getCategoryLicense() == "A2" ||
            fileSharePreference.getCategoryLicense() == "A3" ||
            fileSharePreference.getCategoryLicense() == "A4" ){
            19
        }else if( fileSharePreference.getCategoryLicense() == "B1" ){
            20
        }
        else{
            22
        }
    }
    fun getScorePass(): Int{
        return if (fileSharePreference.getCategoryLicense() == "A1" ){
            21
        }else if (fileSharePreference.getCategoryLicense() == "A2"||
            fileSharePreference.getCategoryLicense() == "A3" ||
            fileSharePreference.getCategoryLicense() == "A4" ){
            23
        }
        else if( fileSharePreference.getCategoryLicense() == "B1" ){
            27
        }
        else{
            32
        }
    }

    fun getDataExamination(folder: String , file: String ){
        viewModelScope.launch {
            listQuestions.clear()
            if (numberExamination.toInt() >= 10){
                listExaminationRandom.clear()
                listExaminationRandom.addAll(examinationRandomDao.getAllExaminationRandom(getCategoryLicense()))
                val index = numberExamination.toInt() - 10
                for (i in index * totalNumberQuestion() ..< (index*totalNumberQuestion())+totalNumberQuestion()){
                    val it = listExaminationRandom[i]
                    listQuestions.add(
                        Question(
                            imageUrl = it.imageUrl,
                            question = it.question,
                            ansA = it.ansA,
                            ansB = it.ansB,
                            ansC = it.ansC,
                            ansD = it.ansD,
                            correctAns = it.correctAns,
                            isImportant = it.isImportant,
                            analysis = it.analysis
                        )
                    )
                }
            }else {
                if (numberExamination.toInt() > readFileUtils.getNumberTest(fileSharePreference.getCategoryLicense())){
                    listExaminationRandom.clear()
                    listExaminationRandom.addAll(examinationRandomDao.getAllExaminationRandom(getCategoryLicense()))
                    listQuestions.addAll(listExaminationRandom.take(25).map {
                            Question(
                                imageUrl = it.imageUrl,
                                question = it.question,
                                ansA = it.ansA,
                                ansB = it.ansB,
                                ansC = it.ansC,
                                ansD = it.ansD,
                                correctAns = it.correctAns,
                                isImportant = it.isImportant,
                                analysis = it.analysis
                            )
                        }
                    )
                    println("data = $listQuestions")
                }else {
                    listQuestions.addAll(readFileUtils.getInformationTest(folder, file))
                    listQuestions.forEach {
                        println(it)
                    }
                }
            }
            _dataExamination.value = ExaminationState.Success(listQuestions)
        }
    }

    fun startExamination(min: Long = TimeUnit.MINUTES.toMillis(getTimeForLicense().toLong())){
        timerCounter?.cancel()
        clearCacheData()
        timerCounter = object : CountDownTimer(min,TimeUnit.SECONDS.toMillis(1)){
            override fun onTick(millisUntilFinished: Long) {
                val data = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                _timerState.value = data
            }
            override fun onFinish() {
                _isFinishedExamination.value = true
            }
        }
        timerCounter?.start()
    }

    fun getAnswerUserCurrent(): Int {
        return checkExaminationManager.getAnswerUserForExamination().size
    }
    fun saveUserAnswerExamination(key : String){
        answerUserMap.putAll(checkExaminationManager.getAnswerUserForExamination())
        val folder  = "${fileSharePreference.getCategoryLicense()}_$key"
        listQuestions.forEachIndexed { index, _ ->
            val history = History(
                pathExam = folder,
                answerExam = answerUserMap[index.toLong()] ?: -1
            )
            historyDao.insertHistory(history)
        }
    }

    fun saveIsWrongImportant(key: String){
        val folder  = "${fileSharePreference.getCategoryLicense()}_IMPORTANT_$key"
        fileSharePreference.saveIsWrongImportant(folder, isPassState.value)
    }
    fun getIsWrongImportant(key: String): Boolean{
        val folder  = "${fileSharePreference.getCategoryLicense()}_IMPORTANT_$key"
        return fileSharePreference.getIsWrongImportant(folder)
    }
    fun getAllAnswerUser(){
        viewModelScope.launch {
            println("pathFolder = $pathFolder")
            _dataExamination.value = ExaminationState.Loading
            val dataList = historyDao.getAllHistoryList(pathFolder)
            answerUserCurrentMap.putAll(dataList.mapIndexed { index, history -> index to history.answerExam }.toMap())
            delay(1000)
            _dataExamination.value = ExaminationState.Success(listQuestions)
        }
    }

    fun saveExaminationLicense(key: String, score: Int){
        fileSharePreference.saveScoreForCategoryLicense("${fileSharePreference.getCategoryLicense()}_$key",score)
    }

    fun getScoreExaminationLicense(key: String): Int{
        return fileSharePreference.getScoreForCategoryLicense(key)
    }

    fun getExaminationLicense(key: String): Int{
        return fileSharePreference.getScoreForCategoryLicense(fileSharePreference.getCategoryLicense()+"_"+key)
    }

    private fun clearCacheData(){
        checkExaminationManager.clearData()
    }

    fun setAnswerByIdQuestion(id: Long, index : Int) {
        checkExaminationManager.setAnswerByIdQuestion(id,index)
    }
    fun getAnswerByIdQuestion(id: Long): Int{
        return checkExaminationManager.getAnswerByIdQuestion(id)
    }
    fun submit(){
        println("SUBMIT = ${checkExaminationManager.checkWrongImportant(listQuestions)}")
        _scoreState.intValue = checkExaminationManager.checkAnswerForTest(listQuestions)
        _isPassState.value = checkExaminationManager.checkWrongImportant(listQuestions)
        _isFinishedExamination.value = true
        timerCounter?.start()
    }
    fun stop() {
        timerCounter?.cancel()
    }

    fun setIsRandomExamination(data: Boolean){
        _isRandomState.value = data
    }

    fun createRandomExamination(){
        _dataExamination.value = ExaminationState.Loading
        val allQuestionList = questionDao.getAllQuestion().toList()
        val dataList = allQuestionList.shuffled().mapIndexed { index, item ->
            item.question = "Câu ${index + 1}${item.question.substring(item.question.indexOfFirst { it ==':' })}"
            item
        }
        _dataExamination.value = ExaminationState.Success(dataList.take(totalNumberQuestion()))
        viewModelScope.launch(Dispatchers.IO) {
            val idCreateExamination = System.currentTimeMillis()
            println("dataList = ${dataList.size}")
            dataList.take(totalNumberQuestion()).forEach {
                val examinationModel = ExaminationRandomModel(
                    idExam = idCreateExamination,
                    categoryLicense = getCategoryLicense(),
                    question = it.question,
                    ansA = it.ansA,
                    ansB = it.ansB,
                    ansC = it.ansC,
                    ansD = it.ansD,
                    correctAns = it.correctAns,
                    isImportant = it.isImportant,
                    imageUrl = it.imageUrl,
                    analysis = it.analysis,
                )
                examinationRandomDao.insertExaminationRandom(examinationModel)
            }
        }
    }

    override fun onCleared() {
        timerCounter?.cancel()
        super.onCleared()
    }
}
sealed class ExaminationState{

    data object Loading : ExaminationState()
    data class Success(val listQuestions: List<Question>) : ExaminationState()
    data class Error(val error: String?) : ExaminationState()
}