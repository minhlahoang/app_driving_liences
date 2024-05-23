package com.example.appdrivinglience.navigaion

import com.example.appdrivinglience.R

object Graph {
    const val OnBoardingScreen = "onBoarding"
    const val RootGraph = "rootGraph"
    const val MainScreenGraph="mainScreenGraph"
    const val TypeLicenseGraph="typeLicenseGraph"
    const val NotificationGraph="notificationGraph"
}

sealed class OnBoardingScreen(val route: String){
     data object OnBoarding : OnBoardingScreen("OnBoardingScreen")
}
sealed class MainScreen(val route: String){
     data object HomeScreen : MainScreen("HomeScreen")
     data object TheoryScreen : MainScreen("TheoryScreen")
     data object TestMockScreen : MainScreen("TestMockScreen")
     data object SignScreen : MainScreen("SignScreen")
     data object SettingScreen : MainScreen("SettingScreen")
}
sealed class TypeLicenseScreen(val route: String, val title: String){
    data object TypeScreen : TypeLicenseScreen("TypeScreen","Chọn hạng bằng thi")

}

sealed class NotificationScreen(val route: String, val title: String) {
    data object CreateNotification : NotificationScreen("CreateNotification","Thông báo")
    data object ListNotification : NotificationScreen("ListNotification","Danh sách thông báo")
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    data object Home : BottomBarScreen(
        route = MainScreen.HomeScreen.route,
        title = "Trang chủ",
        icon = R.drawable.home_2
    )

    data object Theory : BottomBarScreen(
        route = MainScreen.TheoryScreen.route,
        title = "Lý thuyết",
        icon = R.drawable.book_home
    )
    data object TestMock : BottomBarScreen(
        route = MainScreen.TestMockScreen.route,
        title = "Thi thử",
        icon = R.drawable.test_mock_home
    )
    data object Signature : BottomBarScreen(
        route = MainScreen.SignScreen.route,
        title = "Biển báo",
        icon = R.drawable.minus_cirlce
    )

    data object Settings : BottomBarScreen(
        route = MainScreen.SettingScreen.route,
        title = "Cài đặt",
        icon = R.drawable.setting_2
    )
}

sealed class LearnTrickScreen(val route: String, val title : String){
    data object LearnTrick : LearnTrickScreen("LearnTrickScreen","Mẹo thi")
}
sealed class LearnWrongScreen(val route: String, val title : String){
    data object LearnWrong : LearnWrongScreen("LearnWrongScreen","Câu hỏi hay sai")
}
sealed class ResultScreen(val route: String, val title : String){
    data object ResultExamination : ResultScreen("ResultExamination","Kết quả bài thi")
}
sealed class TestExaminationScreen(val route: String, val title : String) {
    data object TestExamination : ResultScreen("TestExamination", "Kết quả bài thi")
}
sealed class HistoryExaminationScreen(val route: String, val title : String){
    data object HistoryExamination : ResultScreen("HistoryExamination","Kết quả bài thi")
}



enum class LEARN (val data: Int) {
    THEORY(1),
    EXAM(2),
    LEARN_SIGN(3),
    TRICK(4),
    WRONG(5),
}