package com.example.appdrivinglience.feature.examination_test_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.CommonErrorScreen

@Composable
fun TestScreen(
    testExaminationViewModel: TestExaminationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickHistory : (String)-> Unit,
    onClick: (String) -> Unit
    ) {
    println("Size Test = ${testExaminationViewModel.getNumberExamination()}")
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 120.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        if (testExaminationViewModel.getNumberExamination() > 0) {
            items(testExaminationViewModel.getNumberExamination()) {
                ItemTest(
                    index = it,
                    testExaminationViewModel = testExaminationViewModel,
                    onClickHistory = onClickHistory
                ) {
                    onClick(it)
                }
            }
        }else {
            item {
                CommonErrorScreen(error = "Hệ thống chưa có dữ liệu của hạng thi ${testExaminationViewModel.getCategoryLicense()}")
            }
        }
    }
}

@Composable
fun ItemTest(index: Int, modifier: Modifier = Modifier,
             testExaminationViewModel: TestExaminationViewModel,
             onClickHistory: (String) -> Unit,
             onClick: (String) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (testExaminationViewModel.getExaminationLicense("${index+1}") == -1){
                colorResource(id = R.color.white)
            }else {
                if (
                    testExaminationViewModel.getIsWrongImportant("${index + 1}") &&
                    testExaminationViewModel.getScorePass()
                    <= testExaminationViewModel.getExaminationLicense("${index+1}")){
                    colorResource(id = R.color.green_primary)
                }else {
                    colorResource(id = R.color.red)
                }
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick("${index + 1}")
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .clickable {
                    onClick("${index + 1}")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .clickable {
                        onClick("${index + 1}")
                    },
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top

            ) {
                Text(
                    text = "Đề số ${index+1}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        fontWeight = FontWeight(700),
                        color = colorResource(id = R.color.dark_ne),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    text = "${testExaminationViewModel.totalNumberQuestion()} câu / ${testExaminationViewModel.getTimeForLicense()} phút",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                        fontWeight = FontWeight(400),
                        color = colorResource(id = R.color.neutra_2),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            if (testExaminationViewModel.getExaminationLicense("${index+1}") != -1) {
                Image(
                    modifier = Modifier.clickable {
                        onClickHistory("${index + 1}")
                    },
                    painter = painterResource(id = R.drawable.activity),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemTest() {
//    ItemTest()
}