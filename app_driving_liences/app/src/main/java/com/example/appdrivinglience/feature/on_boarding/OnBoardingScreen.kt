package com.example.appdrivinglience.feature.on_boarding

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appdrivinglience.R
import com.example.appdrivinglience.feature.model.IntroModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    introModelList: List<IntroModel> = emptyList(),
    onSkip: () -> Unit,
    onNext: () -> Unit,
) {

    val pageState = rememberPagerState {
        introModelList.size
    }

    val scope = rememberCoroutineScope()
    ConstraintLayout {
        val (layoutContainer, layoutFooter) = createRefs();
        HorizontalPager(
            state = pageState,
            Modifier
                .fillMaxSize()
                .constrainAs(layoutContainer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        ) { currentPage ->

            CommonOnBoardingScreen(
                title = introModelList[currentPage].title,
                content = introModelList[currentPage].content,
                idRes = introModelList[currentPage].idRes,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 64.dp)
                .constrainAs(layoutFooter) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            onSkip()
                        },
                    text = stringResource(id = R.string.skip),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold, FontWeight(500)))
                    )

                )
                PageIndicator(introModelList.size, pageState.currentPage)
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable {
                            scope.launch {
                                if (pageState.currentPage == 3) {
                                    onNext()
                                } else {
                                    pageState.scrollToPage(pageState.currentPage + 1);
                                }
                            }
                        },
                    text = stringResource(id = R.string.continous),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.green_primary),
                        fontFamily = FontFamily(Font(R.font.bevietnampro_bold, FontWeight(500)))
                    )
                )

            }
        }
    }

}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            CircleIndicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun CircleIndicator(isSelected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(2.dp)
            .height(10.dp)
            .width(10.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = if (isSelected) R.color.green_primary else R.color.oval_primary))
    )
}

@Preview
@Composable
fun PreviewOnBoardingScreen() {
    val listIntroModel = listOf(
        IntroModel(
            stringResource(id = R.string.day_1),
            stringResource(id = R.string.content_day_1),
            R.drawable.day_1
        ),
        IntroModel(
            stringResource(id = R.string.day_2),
            stringResource(id = R.string.content_day_2),
            R.drawable.day_2
        ),
        IntroModel(
            stringResource(id = R.string.day_3),
            stringResource(id = R.string.content_day_3),
            R.drawable.day_3
        ),
        IntroModel(
            stringResource(id = R.string.day_4),
            stringResource(id = R.string.content_day_4),
            R.drawable.day_4
        )
    )

//    OnBoardingScreen(
//        modifier = Modifier.fillMaxSize(),
//        introModelList = listIntroModel
//    )
}
