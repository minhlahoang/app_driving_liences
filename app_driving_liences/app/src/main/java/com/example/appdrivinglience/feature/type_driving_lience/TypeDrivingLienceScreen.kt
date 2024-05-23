package com.example.appdrivinglience.feature.type_driving_lience

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.database.model.CategoryLicense
import com.example.appdrivinglience.feature.home_screen.HomeViewModel
import com.example.appdrivinglience.feature.model.TypeDriving


@Composable
fun TypeDrivingLicenseScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    listTypeDrivingLicense: List<CategoryLicense>,
    onSelectedItem: (CategoryLicense) -> Unit
) {
    
    var selectedCategory by remember {
        mutableStateOf(homeViewModel.getCategoryLicense())
    }

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
        ) {
        items(listTypeDrivingLicense){
            ItemLicense(typeDriving = it, 
                onSelectedItem = {
                    selectedCategory = it.nameLicense
                    onSelectedItem(it)
                },
                isSelected = selectedCategory == it.nameLicense
            )
        }
    }

}

@Composable
fun ItemLicense(
    modifier: Modifier = Modifier,
    typeDriving: CategoryLicense,
    onSelectedItem : (CategoryLicense)-> Unit,
    isSelected : Boolean
) {
    Row(
        modifier = modifier
            .width(300.dp)
            .padding(top = 16.dp)
            .background(
                color = if (isSelected) colorResource(id = R.color.green_primary) else colorResource(
                    id = R.color.dark_ne
                ),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable {
                onSelectedItem(typeDriving)
            }

            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = typeDriving.nameLicense,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(700),
                color = colorResource(id = R.color.white),
            )
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = typeDriving.description,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.vietnampro_black)),
                fontWeight = FontWeight(300),
                color = colorResource(id = R.color.white),
            )
        )
    }
}

@Preview
@Composable
fun PreviewItemLicense() {
//    TypeDrivingLicenseScreen(
//        modifier =  Modifier.fillMaxSize(),
//        listOf(
//            TypeDriving("1","A1","Xe mô tô 2 bánh có dung tích xi lanh dưới 175 cm3"),
//            TypeDriving("2","A2","Xe mô tô 2 bánh có dung tích xi lanh dưới 175 cm3"),
//            TypeDriving("3","A3","Xe mô tô 3 bánh, xe 3 gác, xe lam, xích lô..."),
//            TypeDriving("4","A4","Xe máy léo nhỏ có trọng tải đến 1000kg"),
//            TypeDriving("5","B1","Ô tô chở người đến 9 chỗ ngồi, Ô tô tải trọng dưới 3,5..."),
//            TypeDriving("6","B2","Tương tự B1, dành cho người hành nghề lái xe"),
//        )
//    )
}