package com.example.appdrivinglience.feature.study_theory_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrivinglience.R
import com.example.appdrivinglience.feature.model.StudyTheoryModel

@Composable
fun StudyTheoryScreen(
    modifier: Modifier = Modifier,
    listStudyTheoryModel: List<StudyTheoryModel>,
    onItemSelected : (StudyTheoryModel) -> Unit
) {

    LazyColumn(modifier =
         modifier.fillMaxSize()
        .padding(horizontal = 16.dp)
             .padding(top = 120.dp, bottom = 100.dp)
    ) {
        items(listStudyTheoryModel){
            ItemTheory(modifier = Modifier.fillMaxWidth(), studyTheoryModel = it , onItemSelected)
        }
    }
}

@Composable
fun ItemTheory(modifier: Modifier, studyTheoryModel: StudyTheoryModel, onItemSelected: (StudyTheoryModel) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                onItemSelected(studyTheoryModel)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable {
                onItemSelected(studyTheoryModel)
            },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(color = colorResource(id = studyTheoryModel.colorTheory)),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = studyTheoryModel.iconRes) , contentDescription = null )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = studyTheoryModel.nameTheory,
// Subtitle2
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                    fontWeight = FontWeight(500),
                    color = colorResource(id = R.color.dark_ne),
                    letterSpacing = 0.02.sp,
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewStudyTheoryScreen(){
    val listTheory = listOf(
        StudyTheoryModel(nameTheory = "Tất cả câu lý thuyết", iconRes = R.drawable.book, colorTheory =  R.color.second_2),
        StudyTheoryModel(nameTheory = "Câu hỏi điểm liệt", iconRes = R.drawable.danger, colorTheory =  R.color.second_4),
        StudyTheoryModel(nameTheory = "Khái niệm và quy tắc", iconRes = R.drawable.note, colorTheory =  R.color.second_3),
        StudyTheoryModel(nameTheory = "Văn hóa và đạo đức lái xe", iconRes = R.drawable.car, colorTheory =  R.color.second_4),
        StudyTheoryModel(nameTheory = "Kỹ thuật lái xe", iconRes = R.drawable.group, colorTheory =  R.color.second_1),
        StudyTheoryModel(nameTheory = "Cấu tạo và sửa chữa", iconRes = R.drawable.edit, colorTheory =  R.color.second_2),
        StudyTheoryModel(nameTheory = "Biển báo đường bộ", iconRes = R.drawable.traffic, colorTheory =  R.color.second_3),
        StudyTheoryModel(nameTheory = "Sa hình", iconRes = R.drawable.picture_frame, colorTheory =  R.color.second_4),)
    
//    StudyTheoryScreen(listStudyTheoryModel = listTheory)
}