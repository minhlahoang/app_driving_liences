package com.example.appdrivinglience.feature.examination_test_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.CommonErrorScreen
import com.example.appdrivinglience.components.CommonLoadingScreen
import com.example.appdrivinglience.feature.model.QuestionModel
import com.example.appdrivinglience.feature.on_boarding.PageIndicator
import com.example.appdrivinglience.feature.study_theory_screen.ItemQuestion
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExaminationMockScreen(
    examinationViewModel: TestExaminationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBack : ()-> Unit,
    onSubmit : ()-> Unit)
{

    LaunchedEffect(key1 = Unit) {
        examinationViewModel.startExamination()
    }
    when(val state = examinationViewModel.dataExamination.value){
        is ExaminationState.Error -> CommonErrorScreen(error = state.error)
        ExaminationState.Loading -> CommonLoadingScreen()
        is ExaminationState.Success -> {
            val statePager = rememberPagerState {
                state.listQuestions.size
            }
            val dataMap = state.listQuestions.mapIndexed { index, question ->  index to Pair(question.correctAns, question.analysis) }.toMap()
            val stateScope = rememberCoroutineScope()
            ConstraintLayout (
                modifier = modifier.fillMaxSize()
            ){
                val (layoutHeader,layoutContainer, layoutFooter) = createRefs()
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
                            onBack()
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription =null)
                    Text(text = examinationViewModel.timeState.value,
                        style = TextStyle(
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        ))
                    Image(
                        modifier = Modifier.clickable {
                            onSubmit()
                        },
                        imageVector = Icons.Default.Check,
                        contentDescription =null)
                }

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .constrainAs(
                            layoutContainer
                        ) {
                            this.top.linkTo(layoutHeader.bottom)
                            this.start.linkTo(parent.start)
                            this.end.linkTo(parent.end)
                        },
                    state = statePager) {currentPage ->

                    val questionModel1 = state.listQuestions[currentPage]
                    val ans = examinationViewModel.getAnswerByIdQuestion(currentPage.toLong())
                    println("ans = $ans")
                    ItemQuestion(
                        questionModel = questionModel1
                        , index = currentPage, dataMap = dataMap ,
                        a = if (ans == 0) ans else -1,
                        b = if (ans == 1) ans else -1,
                        c = if (ans == 2) ans else -1,
                        d = if (ans == 3) ans else -1,
                        viewModel = examinationViewModel) {
                        examinationViewModel.setAnswerByIdQuestion(currentPage.toLong(), it)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 64.dp)
                        .constrainAs(layoutFooter) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val boldText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.bevietnampro_regular) ))) {
                            append("${statePager.currentPage + 1} ")
                        }
                        append("/")
                        withStyle(style = SpanStyle(fontFamily = FontFamily(Font(R.font.bevietnampro_bold) ))) {
                            append("${state.listQuestions.size}")
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colorResource(id = R.color.white))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            modifier = Modifier.clickable {
                                val page = if(statePager.currentPage - 1 < 0) 0 else statePager.currentPage - 1
                                stateScope.launch {
                                    statePager.scrollToPage(page)
                                }
                            },
                            imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        Text(text = boldText)
                        Image(
                            modifier = Modifier.clickable {
                                val page = if(statePager.currentPage + 1 >= state.listQuestions.size) state.listQuestions.size - 1 else statePager.currentPage + 1
                                stateScope.launch {
                                    statePager.scrollToPage(page)
                                }

                            },
                            imageVector = Icons.Default.ArrowForward, contentDescription = null)

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExamination() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
//        ExaminationMockScreen()
    }
}
