package com.example.appdrivinglience.feature.study_sign_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.appdrivinglience.MainActivity
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.dialog.SignDialog
import com.example.appdrivinglience.database.model.SignModel

@Composable
fun StudySignScreen(modifier: Modifier = Modifier) {
    SectionSign(modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionSign(
    modifier: Modifier = Modifier,
    studySignViewModel: StudySignViewModel = hiltViewModel(), ) {
    val pages = listOf("A","B","C","D","E")
    val statePager = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        5
    }

    val pageScope = rememberCoroutineScope()
    var isSignDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        if (isSignDialog) {
            when(val state = studySignViewModel.signState.value){
                is SignState.DataSign -> {
                    SignDialog(signModel = state.data  ){
                        isSignDialog = false
                    }
                }
                SignState.None ->{

                }
            }
        }

        ScrollableTabRow(
            edgePadding = 5.dp,
            selectedTabIndex =
            statePager.currentPage,
            indicator = {

            }
        ) {
            pages.forEachIndexed { index, _ ->
                when (index) {
                    0 -> {
                        TabHeader(title = "Biến báo cấm", selected = index == statePager.currentPage)
                    }

                    1 -> {
                        TabHeader(title = "Biến báo nguy hiểm", selected = index == statePager.currentPage)
                    }

                    2 -> {
                        TabHeader(title = "Biến báo phụ", selected = index == statePager.currentPage)
                    }

                    3 -> {
                        TabHeader(title = "Biến chỉ dân", selected = index == statePager.currentPage)
                    }

                    4 -> {
                        TabHeader(title = "Biến hiệu lệnh", selected = index == statePager.currentPage)
                    }
                }
            }
        }
        HorizontalPager(
            state = statePager,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {currentPage ->
            LazyVerticalGrid(columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    )) {

                when(currentPage){
                    0 -> {
                        items(MainActivity.appBanSignList){
                            ItemSign(signModel = it){
                                isSignDialog = true
                                studySignViewModel.setSignModel(it)
                            }
                        }
                    }
                    1 -> {
                        items(MainActivity.appDangerousSignList){
                            ItemSign(signModel = it){
                                isSignDialog = true

                                studySignViewModel.setSignModel(it)

                            }
                        }
                    }
                    2 -> {
                        items(MainActivity.appTempSignList){
                            ItemSign(signModel = it){
                                isSignDialog = true

                                studySignViewModel.setSignModel(it)

                            }
                        }
                    }
                    3 -> {
                        items(MainActivity.appGuideSignList){
                            ItemSign(signModel = it){
                                isSignDialog = true

                                studySignViewModel.setSignModel(it)

                            }
                        }
                    }
                    4 -> {
                        items(MainActivity.appControlSignList){
                            ItemSign(signModel = it){
                                isSignDialog = true

                                studySignViewModel.setSignModel(it)

                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun TabHeader(title: String, selected: Boolean) {
    Tab(
        selected = selected,
        onClick = { },
        text = {
            Text(text = title,
                style = TextStyle(
                    color = if (selected) colorResource(id = R.color.green_primary) else MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                )
            )
        }
    )
}

@Composable
fun ItemSign(modifier: Modifier = Modifier,
             signModel: SignModel,
             onClick : (SignModel)-> Unit) {
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
            .clickable {
                onClick(signModel)
            }
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 10.dp),

                painter = rememberAsyncImagePainter(signModel.imgUrlSign) ,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = signModel.nameSign,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.dark_ne),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewItemSign() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SectionSign()
    }
}