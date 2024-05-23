package com.example.appdrivinglience.feature.trick_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.CommonErrorScreen
import com.example.appdrivinglience.components.CommonLoadingScreen
import kotlinx.coroutines.delay

@Composable
fun TrickViewScreen(
    modifier: Modifier = Modifier,
    trickModel: TrickViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        trickModel.getAllTrick()
    }
    val state = trickModel.trickState.value
    println("State = $state")
    when (state) {
        is TrickState.Error -> {
            CommonErrorScreen(error = state.error)
        }

        TrickState.Loading -> {
           CommonLoadingScreen()
        }

        is TrickState.Success<*> -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 120.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val trickModelList = (state.trickList as List<*>).mapNotNull { it as? TrickModel }
                items(trickModelList){
                    ItemTrick(trickModel = it)
                }
            }
        }
    }

}


@Composable
fun ItemTrick(modifier: Modifier = Modifier, trickModel: TrickModel) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = trickModel.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                    color = colorResource(id = R.color.dark_ne),
                    letterSpacing = 0.02.sp,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = trickModel.content,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                    color = colorResource(id = R.color.neutra_2),
                    letterSpacing = 0.03.sp,
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewTrickScreen() {
//    ItemTrick(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    );
}