package com.example.appdrivinglience.navigaion.graphs

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.appdrivinglience.R
import com.example.appdrivinglience.components.dialog.CommonDialog
import com.example.appdrivinglience.components.dialog.CommonExitDialog
import com.example.appdrivinglience.feature.examination_test_screen.ExaminationMockScreen
import com.example.appdrivinglience.feature.examination_test_screen.TestExaminationViewModel
import com.example.appdrivinglience.feature.examination_test_screen.TestScreen
import com.example.appdrivinglience.feature.history_examination.HistoryExaminationScreen
import com.example.appdrivinglience.feature.type_driving_lience.TypeDrivingLicenseScreen
import com.example.appdrivinglience.feature.home_screen.HomeScreen
import com.example.appdrivinglience.feature.home_screen.HomeViewModel
import com.example.appdrivinglience.feature.learn_wrong.LearnWrongViewModel
import com.example.appdrivinglience.feature.model.StudyTheoryModel
import com.example.appdrivinglience.feature.model.TypeDriving
import com.example.appdrivinglience.feature.notification_screen.CreateNotificationScreen
import com.example.appdrivinglience.feature.notification_screen.NotificationScreen
import com.example.appdrivinglience.feature.result_show_screen.ResultShowScreen
import com.example.appdrivinglience.feature.setting.SettingScreen
import com.example.appdrivinglience.feature.study_sign_screen.StudySignScreen
import com.example.appdrivinglience.feature.study_theory_screen.ExaminationScreen
import com.example.appdrivinglience.feature.study_theory_screen.StudyTheoryScreen
import com.example.appdrivinglience.feature.study_theory_screen.StudyTheoryScreenViewModel
import com.example.appdrivinglience.feature.trick_screen.TrickViewScreen
import com.example.appdrivinglience.navigaion.BottomBarScreen
import com.example.appdrivinglience.navigaion.Graph
import com.example.appdrivinglience.navigaion.Graph.TypeLicenseGraph
import com.example.appdrivinglience.navigaion.HistoryExaminationScreen
import com.example.appdrivinglience.navigaion.LEARN
import com.example.appdrivinglience.navigaion.LearnTrickScreen
import com.example.appdrivinglience.navigaion.LearnWrongScreen
import com.example.appdrivinglience.navigaion.MainScreen
import com.example.appdrivinglience.navigaion.NotificationScreen
import com.example.appdrivinglience.navigaion.ResultScreen
import com.example.appdrivinglience.navigaion.TestExaminationScreen
import com.example.appdrivinglience.navigaion.TypeLicenseScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController = rememberNavController(),
    onChangedTheme: (Boolean) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val multiplePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(permissions = listOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.SCHEDULE_EXACT_ALARM,
                Manifest.permission.USE_EXACT_ALARM,
                ))
    } else {
        rememberMultiplePermissionsState(permissions = listOf())
    }

    var screenCurrentState by remember {
        mutableStateOf(BottomBarScreen.Home.title)
    }
    var isDialogPrepareExamination by remember {
        mutableStateOf(false)
    }

    var isDialogSubmittedExamination by remember {
        mutableStateOf(false)
    }
    var isCreateRandomExamination by remember {
        mutableStateOf(false)
    }
    val examinationViewModel = hiltViewModel<TestExaminationViewModel>()
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_START) {
                    multiplePermission.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )


//    val navController = rememberNavController()
    val screensList = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Theory,
        BottomBarScreen.TestMock,
        BottomBarScreen.Signature,
        BottomBarScreen.Settings
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            if (screenCurrentState != BottomBarScreen.Home.title
                && screenCurrentState != TestExaminationScreen.TestExamination.route ) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.green_primary),
                        titleContentColor = colorResource(id = R.color.white)
                    ),
                    title = {
                        Text(text = screenCurrentState)
                    },
                    actions = {
                        if (screenCurrentState == BottomBarScreen.TestMock.title){
                            IconButton(onClick = { isCreateRandomExamination = !isCreateRandomExamination }) {
                                Icon(Icons.Default.Add, contentDescription = "Search", tint = MaterialTheme.colorScheme.background)
                            }
                        }
                    },
                    navigationIcon = {
                        if (screenCurrentState == TypeLicenseScreen.TypeScreen.title ||
                            screenCurrentState == BottomBarScreen.Theory.title ||
                            screenCurrentState == LearnTrickScreen.LearnTrick.title ||
                            screenCurrentState == NotificationScreen.CreateNotification.title ||
                            screenCurrentState == NotificationScreen.ListNotification.title ||
                            screenCurrentState == LearnWrongScreen.LearnWrong.title ||
                            screenCurrentState == BottomBarScreen.TestMock.title ||
                            screenCurrentState == BottomBarScreen.Signature.title ||
                            screenCurrentState == HistoryExaminationScreen.HistoryExamination.route
                        ) {

                            IconButton(onClick = {
                                val destination = navController.previousBackStackEntry?.destination?.route
                                if (destination == BottomBarScreen.Home.route) {
                                    screenCurrentState = BottomBarScreen.Home.title
                                }else {
                                    val preScreen = screensList.singleOrNull { it.route == destination }
                                    preScreen?.let {
                                        screenCurrentState = it.title
                                    }?: run {
                                        val preScreen = NotificationScreen.CreateNotification.route
                                        if (destination == preScreen){
                                            screenCurrentState = NotificationScreen.CreateNotification.title
                                        }
                                    }
                                }
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,

                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (screensList.any { it.title == screenCurrentState
                        && screenCurrentState != BottomBarScreen.TestMock.route
                        && screenCurrentState != BottomBarScreen.Signature.title
            }) {
                BottomBar(screensList = screensList, navController = navController) {
                    screenCurrentState = it.title
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            route = Graph.MainScreenGraph,
            startDestination = MainScreen.HomeScreen.route
        ) {

            composable(MainScreen.HomeScreen.route) {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    modifier = Modifier.fillMaxWidth(),
                    selectedLicenseType = {
                        screenCurrentState = TypeLicenseScreen.TypeScreen.title
                        navController.navigate(TypeLicenseScreen.TypeScreen.route)
                    }
                ) {
                    when (it) {
                        LEARN.THEORY -> {
                            screenCurrentState = BottomBarScreen.Theory.title
                            navController.navigate(MainScreen.TheoryScreen.route)
                        }

                        LEARN.EXAM -> {
                            screenCurrentState = BottomBarScreen.TestMock.title
                            navController.navigate(MainScreen.TestMockScreen.route)
                        }

                        LEARN.WRONG -> {
                            screenCurrentState = LearnWrongScreen.LearnWrong.title
                            val data = 99
                            navController.navigate("${LearnWrongScreen.LearnWrong.route}?idCategory=${data}")
                        }

                        LEARN.LEARN_SIGN -> {
                            screenCurrentState = BottomBarScreen.Signature.title
                            navController.navigate(BottomBarScreen.Signature.route)
                        }

                        LEARN.TRICK -> {
                            screenCurrentState = LearnTrickScreen.LearnTrick.title
                            navController.navigate(LearnTrickScreen.LearnTrick.route)
                        }
                    }
                }
            }
            composable(MainScreen.TheoryScreen.route) {
                val listTheory = listOf(
                    StudyTheoryModel(
                        nameTheory = "Tất cả câu lý thuyết",
                        iconRes = R.drawable.book,
                        colorTheory = R.color.second_2,
                        idCategory = 0
                    ),
                    StudyTheoryModel(
                        nameTheory = "Câu hỏi điểm liệt",
                        iconRes = R.drawable.danger,
                        colorTheory = R.color.second_4,
                        idCategory = 1
                    ),
                    StudyTheoryModel(
                        nameTheory = "Khái niệm và quy tắc",
                        iconRes = R.drawable.note,
                        colorTheory = R.color.second_3,
                        idCategory = 2
                    ),
                    StudyTheoryModel(
                        nameTheory = "Văn hóa và đạo đức lái xe",
                        iconRes = R.drawable.car,
                        colorTheory = R.color.second_4,
                        idCategory = 3
                    ),
                    StudyTheoryModel(
                        nameTheory = "Kỹ thuật lái xe",
                        iconRes = R.drawable.group,
                        colorTheory = R.color.second_1,
                        idCategory = 4
                    ),
                    StudyTheoryModel(
                        nameTheory = "Cấu tạo và sửa chữa",
                        iconRes = R.drawable.edit,
                        colorTheory = R.color.second_2,
                        idCategory = 5
                    ),
                    StudyTheoryModel(
                        nameTheory = "Biển báo đường bộ",
                        iconRes = R.drawable.traffic,
                        colorTheory = R.color.second_3,
                        idCategory = 6
                    ),
                    StudyTheoryModel(
                        nameTheory = "Sa hình",
                        iconRes = R.drawable.picture_frame,
                        colorTheory = R.color.second_4,
                        idCategory = 7
                    ),
                )

                StudyTheoryScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    listStudyTheoryModel = listTheory
                ){
                    navController.navigate("${LearnWrongScreen.LearnWrong.route}?idCategory=${it.idCategory}")
                }
            }
            composable(ResultScreen.ResultExamination.route) {
                ResultShowScreen(
                    viewModel = examinationViewModel,
                    modifier = Modifier.fillMaxSize(), onAccept = {
                    screenCurrentState = BottomBarScreen.Home.title
                    navController.navigate(BottomBarScreen.Home.route){
                        popUpTo(BottomBarScreen.Home.route){
                            inclusive = true
                        }
                    }
                }, onCancel = {
                    screenCurrentState = BottomBarScreen.Home.title
                    navController.navigate(BottomBarScreen.Home.route){
                        popUpTo(BottomBarScreen.Home.route){
                            inclusive = true
                        }
                    }
                })
            }
            composable(TestExaminationScreen.TestExamination.route){
                if(isDialogSubmittedExamination){
                    CommonExitDialog(
                        title = "Bạn có chắc chắn muốn thoát khỏi\nđề thi đang làm không?",
                        onCancel = {
                            isDialogSubmittedExamination = !isDialogSubmittedExamination
                        },
                        onAccept = {
                            isDialogSubmittedExamination = !isDialogSubmittedExamination
                            examinationViewModel.submit()
                            screenCurrentState = ResultScreen.ResultExamination.title
                            navController.navigate(ResultScreen.ResultExamination.route)
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Thời gian còn lại: ${examinationViewModel.timeState.value}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                    fontWeight = FontWeight(400),
                                    color = colorResource(id = R.color.dark_ne),
                                    textAlign = TextAlign.Center,
                                )
                            )

                            Text(
                                text = "Số câu chưa làm: ${examinationViewModel.totalNumberQuestion() - examinationViewModel.getAnswerUserCurrent()}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                    fontWeight = FontWeight(400),
                                    color =colorResource(id = R.color.dark_ne),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                }
                ExaminationMockScreen(
                    examinationViewModel = examinationViewModel,
                    onBack = {
                    screenCurrentState = BottomBarScreen.Home.title
                    navController.navigate(BottomBarScreen.Home.route) },
                    onSubmit = {
                        examinationViewModel.setIsRandomExamination(false)
                        isDialogSubmittedExamination = !isDialogSubmittedExamination
                    })
            }
            composable(HistoryExaminationScreen.HistoryExamination.route){
                HistoryExaminationScreen(examinationViewModel)
            }
            composable(MainScreen.TestMockScreen.route) {

                if (isDialogPrepareExamination){
                    CommonDialog(
                        title = "Thi thử lý thuyết hạng ${examinationViewModel.getCategoryLicense()}",
                        nameButton = "BẮT ĐẦU LÀM BÀI THI",
                        onDismiss = {
                            isDialogPrepareExamination = !isDialogPrepareExamination
                        },
                        onClick = {
                            val folder = homeViewModel.getCategoryLicense()
                            val file   = "${examinationViewModel.numberExamination}.txt"
                            isDialogPrepareExamination = !isDialogPrepareExamination
                            examinationViewModel.setIsRandomExamination(false)
                            examinationViewModel.getDataExamination(folder,file)
                            screenCurrentState = TestExaminationScreen.TestExamination.route
                            navController.navigate(TestExaminationScreen.TestExamination.route)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            val buildText = buildAnnotatedString {
                                withStyle(style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold))
                                )){
                                    append("Tuyệt đối không được làm sai câu hỏi điểm liệt,")
                                }
                                withStyle(style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular))
                                )){
                                    append(" trả lời sai đồng nghĩa với việc")
                                }
                                withStyle(style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_bold)),
                                    color = colorResource(id = R.color.red)
                                )){
                                    append("\"KHÔNG ĐẠT\"")
                                }
                                withStyle(style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular))
                                )){
                                    append(" dù cho các câu khác trả lời đúng!")
                                }
                            }
                            Text(
                                text = "Tổng số câu hỏi: ${examinationViewModel.totalNumberQuestion()}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                    fontWeight = FontWeight(500),
                                    color = colorResource(id = R.color.dark_ne),
                                )
                            )
                            Text(
                                text = "Thời gian làm bài: ${examinationViewModel.getTimeForLicense()} phút",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                    fontWeight = FontWeight(500),
                                    color = colorResource(id = R.color.dark_ne),
                                )
                            )
                            Text(
                                text = "Số câu điểm đạt: ${examinationViewModel.getScorePass()}/${examinationViewModel.totalNumberQuestion()}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                    fontWeight = FontWeight(500),
                                    color = colorResource(id = R.color.dark_ne),
                                )
                            )
                            Text(text = buildText)
                        }
                    }
                }
                
                if (isCreateRandomExamination){
                    CommonDialog(
                        title = "Tạo đề thi ngẫu nhiên",
                        nameButton = "Tạo đề thi",
                        onClick = {
                            isCreateRandomExamination = !isCreateRandomExamination
                            examinationViewModel.setIsRandomExamination(true)
                            examinationViewModel.createRandomExamination()

                        },
                        onDismiss = {
                            isCreateRandomExamination = !isCreateRandomExamination
                        },
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Đề thi ngẫu nhiên được tạo nên từ các câu hỏi trong ngân hàng câu hỏi. Đề thi sẽ có cấu trúc giống đề thi thật. Chúc bạn thi tốt!",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.bevietnampro_regular)),
                                fontWeight = FontWeight(400),
                                color = colorResource(id = R.color.dark_ne),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                
                TestScreen(
                    onClickHistory = {
                        val folder = homeViewModel.getCategoryLicense()
                        val file   = "$it.txt"
                        examinationViewModel.pathFolder = "${folder}_$it"
                        examinationViewModel.getDataExamination(folder,file)
                        screenCurrentState = HistoryExaminationScreen.HistoryExamination.route
                        navController.navigate(HistoryExaminationScreen.HistoryExamination.route)
                    }
                ) {
                    val data = it.toIntOrNull()
                    if (data != null) {

                        examinationViewModel.numberExamination = "$data"
                        isDialogPrepareExamination = !isDialogPrepareExamination
                    }
                }
            }
            composable(MainScreen.SignScreen.route) {
                StudySignScreen()
            }
            composable(MainScreen.SettingScreen.route) {
                SettingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    onNotificationNext = {
                        screenCurrentState = NotificationScreen.CreateNotification.title
                        navController.navigate(NotificationScreen.CreateNotification.route)
                    },
                    onListNotificationNext = {
                        screenCurrentState = NotificationScreen.ListNotification.title
                        navController.navigate(NotificationScreen.ListNotification.route)
                    }
                ) {
                    onChangedTheme(it)
                }
            }
            composable(LearnTrickScreen.LearnTrick.route) {
                TrickViewScreen(modifier = Modifier.fillMaxSize())
            }
            composable("${LearnWrongScreen.LearnWrong.route}?idCategory={idCategory}",
                arguments = listOf(
                    navArgument("idCategory") {
                        type = NavType.LongType
                    }
                )
            ) {
                val idCategory = it.arguments?.getLong("idCategory")
                idCategory?.let {
                    if (it != 99L) {
                        val learnViewModel = hiltViewModel<StudyTheoryScreenViewModel>()
                        ExaminationScreen(
                            listQuestionModel = listOf(),
                            modifier = Modifier.fillMaxSize(),
                            idCategory = it,
                            viewModel = learnViewModel
                        )
                    }else {
                        val learnWrongViewModel = hiltViewModel<LearnWrongViewModel>()
                        ExaminationScreen(
                            listQuestionModel = listOf(),
                            modifier = Modifier.fillMaxSize(),
                            viewModel = learnWrongViewModel
                        )
                    }
                }?: run {
                    val learnWrongViewModel = hiltViewModel<LearnWrongViewModel>()
                    ExaminationScreen(
                        listQuestionModel = listOf(),
                        modifier = Modifier.fillMaxSize(),
                        viewModel = learnWrongViewModel
                    )
                }

            }
            composable(NotificationScreen.CreateNotification.route) {
                CreateNotificationScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 90.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                ){
                    screenCurrentState = NotificationScreen.ListNotification.title
                    navController.navigate(NotificationScreen.ListNotification.route)
                }
            }
            composable(NotificationScreen.ListNotification.route){
                NotificationScreen(modifier = Modifier.fillMaxSize())
            }
//            navigation(
//                startDestination = NotificationScreen.CreateNotification.route,
//                route = NotificationGraph
//            ) {
//
//            }

            navigation(
                startDestination = TypeLicenseScreen.TypeScreen.route,
                route = TypeLicenseGraph
            ) {
                composable(TypeLicenseScreen.TypeScreen.route) {
                    TypeDrivingLicenseScreen(
                        homeViewModel,
                        listTypeDrivingLicense = homeViewModel.listCategoryLicense.toList()
                    ) {
                        homeViewModel.saveCategoryLicense(it.nameLicense)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    screensList: List<BottomBarScreen>,
    navController: NavHostController,
    onNavigate: (BottomBarScreen) -> Unit
) {

    NavigationBar {
        screensList.forEach {
            AddItem(
                screensList,
                screen = it,
                navController = navController
            ) {
                onNavigate(it)
            }
        }

    }

}

@Composable
fun RowScope.AddItem(
    screensList: List<BottomBarScreen>,
    screen: BottomBarScreen,
    navController: NavHostController,
    onNavigate: (BottomBarScreen) -> Unit
) {
    val route = navController.currentBackStackEntry?.destination?.route
    NavigationBarItem(
        selected = screensList.any { it.route == route },
        label = { Text(text = screen.title) },
        onClick = {
            onNavigate(screen)
            navController.navigate(screen.route)
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = null
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopBar(title: String) {

    val currentDestination =
        TopAppBar(title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
}