package com.example.appdrivinglience.feature.history_examination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.CommonErrorScreen
import com.example.appdrivinglience.components.CommonLoadingScreen
import com.example.appdrivinglience.feature.examination_test_screen.ExaminationState
import com.example.appdrivinglience.feature.examination_test_screen.TestExaminationViewModel
import com.example.appdrivinglience.feature.study_theory_screen.ItemQuestion
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryExaminationScreen( examinationViewModel: TestExaminationViewModel = hiltViewModel(),
                              modifier: Modifier = Modifier) {


    LaunchedEffect(key1 = Unit) {
        examinationViewModel.getAllAnswerUser()

    }
    when (val state = examinationViewModel.dataExamination.value) {
        is ExaminationState.Error -> CommonErrorScreen(error = state.error)
        ExaminationState.Loading -> CommonLoadingScreen()
        is ExaminationState.Success -> {
            val statePager = rememberPagerState {
                state.listQuestions.size
            }
            val dataMap = state.listQuestions.mapIndexed { index, question ->
                index to Pair(
                    question.correctAns,
                    question.analysis
                )
            }.toMap()
            val stateScope = rememberCoroutineScope()
            ConstraintLayout(
                modifier = modifier.fillMaxSize()
            ) {
                val (layoutHeader, layoutContainer, layoutFooter) = createRefs()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.green_primary))
                        .padding(vertical = 32.dp, horizontal = 16.dp)
                        .constrainAs(layoutHeader) {
                            this.top.linkTo(parent.top)
                            this.start.linkTo(parent.start)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier.clickable {
                            examinationViewModel.stop()
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                    Text(
                        text = "Kết quả thi",
                        style = TextStyle(
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        )
                    )
                }

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .constrainAs(
                            layoutContainer
                        ) {
                            this.top.linkTo(layoutHeader.bottom)
                        },
                    state = statePager
                ) { currentPage ->

                    val questionModel1 = state.listQuestions[currentPage]
                    val ans = examinationViewModel.answerUserCurrentMap[currentPage]
                    println("${examinationViewModel.answerUserCurrentMap} currentPage = $currentPage - ansUser = $ans - qustion = ${questionModel1.correctAns}")
                    if (ans == questionModel1.correctAns) {

                        ItemQuestion(
                            questionModel = questionModel1, index = currentPage, dataMap = dataMap,
                            a = if (ans == 0) ans else -1,
                            b = if (ans == 1) ans else -1,
                            c = if (ans == 2) ans else -1,
                            d = if (ans == 3) ans else -1,
                            isHistory = true,
                            viewModel = examinationViewModel
                        ) {
                        }
                    }else {
                        ItemQuestion(
                            questionModel = questionModel1, index = currentPage, dataMap = dataMap,
                            a = if (questionModel1.correctAns == 0) questionModel1.correctAns else if (ans == 0) ans else -1,
                            b = if (questionModel1.correctAns == 1) questionModel1.correctAns else if (ans == 1) ans else -1,
                            c = if (questionModel1.correctAns == 2) questionModel1.correctAns else if (ans == 2) ans else -1,
                            d = if (questionModel1.correctAns == 3) questionModel1.correctAns else if (ans == 3) ans else -1,
                            isHistory = true,
                            viewModel = examinationViewModel
                        ) {
                        }
                    }
                }
            }
        }
    }
}