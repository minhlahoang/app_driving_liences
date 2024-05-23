package com.example.appdrivinglience.feature.examination_test_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.appdrivinglience.database.model.Question

class CheckExaminationManager {

    private val answerQuestionMap = hashMapOf<Long, Int>()


    fun getAnswerByIdQuestion(id: Long): Int {
        println(answerQuestionMap);
        return answerQuestionMap[id] ?: -1
    }

    fun setAnswerByIdQuestion(id: Long , index: Int) {
        answerQuestionMap[id] = index
    }

    fun checkAnswerForTest(dataListQuestions : List<Question>): Int{
        var count = 0
        dataListQuestions.forEachIndexed { index, question ->
            if(answerQuestionMap[index.toLong()] == question.correctAns) count++
        }
        return count
    }
    fun checkWrongImportant(dataListQuestions: List<Question>): Boolean{
        return dataListQuestions.filter { it.isImportant }
            .mapIndexed { index, question ->
                index to question.correctAns
            }
            .any {
            answerQuestionMap[it.first.toLong()] != it.second
        }
    }
    fun clearData(){
        answerQuestionMap.clear()
    }
    fun getAnswerUserForExamination(): HashMap<Long, Int>{
        return answerQuestionMap
    }
}