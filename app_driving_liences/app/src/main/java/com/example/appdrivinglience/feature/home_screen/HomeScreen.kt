package com.example.appdrivinglience.feature.home_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.dialog.ResultDialogScreen
import com.example.appdrivinglience.database.model.CategoryLicense
import com.example.appdrivinglience.feature.notification_screen.NotificationViewModel
import com.example.appdrivinglience.navigaion.LEARN

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    selectedLicenseType : () -> Unit,
    selectedLearn :(LEARN)-> Unit,
) {
    val notificationViewModel = hiltViewModel<NotificationViewModel>()
    LaunchedEffect(key1 = true) {
        homeViewModel.getAllCategoryLicense()
        notificationViewModel.rescheduleAlarms()
    }

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    val dataTypeLicense = homeViewModel.categoryLicenseState.value

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            if (isShowDialog){
                Dialog(onDismissRequest = { isShowDialog = !isShowDialog }) {
                    ResultDialogScreen(
                        percentage = homeViewModel.getPercentage()
                    ){
                        isShowDialog = !isShowDialog
                    }
                }
            }
            Column(
                modifier = modifier
                    .clickable {
                        selectedLicenseType()
                    }
                    .width(360.dp)
                    .height(200.dp)
                    .background(
                        color = colorResource(id = R.color.green_primary),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
                    .padding(start = 16.dp, top = 71.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Chúc bạn học tốt!",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        fontWeight = FontWeight(700),
                        color = colorResource(id = R.color.white),
                    )
                )
                Spacer(modifier = Modifier.height(19.dp))
                when(dataTypeLicense){
                    is HomeState.Error -> {
                        print("error = ${dataTypeLicense.error}")
                    }
                    HomeState.Loading -> {
                    }
                    is HomeState.Success<*> -> {
                        val data = (dataTypeLicense.listCategoryQuestion as List<*>).map { it -> it as CategoryLicense}.map { it.nameLicense }.toList()
                        Row(
                            modifier = modifier
                                .width(328.dp)
                                .height(66.dp)
                                .background(
                                    color = colorResource(id = R.color.white),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                                .padding(horizontal = 26.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween


                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.ellipse_1),
                                    contentDescription = null
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.motorcycle),
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = null
                                )
                            }

                            Text(
                                text = "Hạng thi ${if(homeViewModel.getCategoryLicense().isEmpty()) data[0] else homeViewModel.getCategoryLicense()} ",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                                    fontWeight = FontWeight(700),
                                    color = colorResource(id = R.color.dark_ne),
                                    textAlign = TextAlign.Center,
                                )
                            )
                            Image(painter = painterResource(id = R.drawable.pen), contentDescription = null)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Tình trạng",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    color = MaterialTheme.colorScheme.secondary,
                )
            )
            CardHeader(
                homeViewModel.getPercentage(),
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ){
                isShowDialog = !isShowDialog
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Lộ trình ôn thi",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    color = MaterialTheme.colorScheme.secondary,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier =
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
                ) {
                ItemLearnAndTest(
                    "Học lý\n" +
                            "thuyết", R.color.green_primary, R.drawable.group_female_work
                ){
                    selectedLearn(LEARN.THEORY)
                    println("THEORY")

                }
                Spacer(modifier = Modifier.width(16.dp))
                ItemLearnAndTest(
                    "Thi sát\nhạch", R.color.blue, R.drawable.group_male_work
                ){
                    println("EXAM")
                    selectedLearn(LEARN.EXAM)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                ItemLearnAndTestThreeColumn(
                    "Học biển\n" +
                            "báo", R.color.red, R.drawable.traffic_learn
                ){
                    selectedLearn(LEARN.LEARN_SIGN)
                }
                Spacer(modifier = Modifier.width(16.dp))
                ItemLearnAndTestThreeColumn(
                    "Mẹo thi\n", R.color.pupple, R.drawable.mask_group
                ){
                    selectedLearn(LEARN.TRICK)
                }
                Spacer(modifier = Modifier.width(16.dp))
                ItemLearnAndTestThreeColumn(
                    "Câu hỏi\n" +
                            "hay sai", R.color.orange, R.drawable.warming_learn
                ){
                    selectedLearn(LEARN.WRONG)
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ItemLearnAndTest(
    title: String,
    @DrawableRes idRes: Int,
    img: Int,
    modifier: Modifier = Modifier,
    onSelectedItem : () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = idRes),
        ),
        modifier = modifier
            .width(160.dp)
            .height(136.dp)
            .padding(top = 16.dp)
            .clickable {
                onSelectedItem()
            }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.white),
                    letterSpacing = 0.02.sp,
                )
            )
            Image(painter = painterResource(id = img), contentDescription = null)

        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ItemLearnAndTestThreeColumn(
    title: String,
    @DrawableRes idRes: Int,
    img: Int,
    modifier: Modifier = Modifier,
    onSelectedItemLearnAndTest :() -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = idRes),
        ),
        modifier = modifier
            .width(104.dp)
            .height(136.dp)
            .padding(top = 16.dp)
            .clickable {
                onSelectedItemLearnAndTest()
            }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    fontWeight = FontWeight(700),
                    color = colorResource(id = R.color.white),
                    letterSpacing = 0.02.sp,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = img), contentDescription = null)
        }
    }
}
@Composable
fun CardHeader(percentage: Int ,modifier: Modifier = Modifier, onListener : ()-> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clickable {
                onListener()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)

            ,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_1),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(id = R.drawable.people),
                    modifier = Modifier.size(50.dp),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        print("Show dialog")
                    },
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "NỖ LỰC GẶT THÀNH CÔNG!",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        color = colorResource(id = R.color.red),
                    )
                )
                Text(
                    text = "Tỉ lệ đỗ của bạn: $percentage%",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                        color = colorResource(id = R.color.dark_ne),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
//    HomeScreen(modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
fun PreviewItemLearnAndTest() {
//    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
//        ItemLearnAndTest(
//            "Học lý\n" +
//                    "thuyết", R.color.green_primary, R.drawable.group_female_work
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        ItemLearnAndTest(
//            "Thi sát\nhạch", R.color.blue, R.drawable.group_male_work
//        )
//    }
}
@Preview
@Composable
fun PreviewItemLearnAndTest1() {
//    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
//        ItemLearnAndTestThreeColumn(
//            "Học biển\n" +
//                    "báo", R.color.red, R.drawable.traffic_learn
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        ItemLearnAndTestThreeColumn(
//            "Mẹo thi", R.color.pupple, R.drawable.mask_group
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        ItemLearnAndTestThreeColumn(
//            "Câu hỏi\n" +
//                    "hay sai", R.color.orange, R.drawable.warming_learn
//        )
//    }
}