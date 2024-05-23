package com.example.appdrivinglience.navigaion.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.appdrivinglience.R
import com.example.appdrivinglience.feature.model.IntroModel
import com.example.appdrivinglience.feature.on_boarding.OnBoardingScreen
import com.example.appdrivinglience.navigaion.Graph
import com.example.appdrivinglience.navigaion.OnBoardingScreen


fun NavGraphBuilder.onBoardingNavGraph(navHostController: NavController){
    navigation(route= Graph.OnBoardingScreen, startDestination = OnBoardingScreen.OnBoarding.route){
        composable(OnBoardingScreen.OnBoarding.route){
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
            OnBoardingScreen(
                modifier = Modifier.fillMaxSize(),
                introModelList = listIntroModel,
                onSkip = {
                    navHostController.navigate(Graph.MainScreenGraph)
                }) {
                navHostController.navigate(Graph.MainScreenGraph)
            }
        }
    }
}