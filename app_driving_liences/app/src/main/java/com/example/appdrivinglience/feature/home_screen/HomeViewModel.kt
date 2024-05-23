package com.example.appdrivinglience.feature.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.core.FileSharePreference
import com.example.appdrivinglience.core.ReadFileUtils
import com.example.appdrivinglience.database.dao.CategoryLicenseDao
import com.example.appdrivinglience.database.dao.CategoryQuestionDao
import com.example.appdrivinglience.database.model.CategoryLicense
import com.example.appdrivinglience.database.model.CategoryQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryLicenseDao: CategoryLicenseDao,
    private val categoryQuestionDao: CategoryQuestionDao,
    private val fileStore: FileSharePreference,
    private val fileUtils : ReadFileUtils
) : ViewModel() {
    private val categoryMutableStateFlow: MutableStateFlow<List<CategoryLicense>> = MutableStateFlow(listOf())
    val categoryFlow: StateFlow<List<CategoryLicense>> = categoryMutableStateFlow

    private val _categoryLicenseState = mutableStateOf<HomeState>(HomeState.Loading)
    val categoryLicenseState: State<HomeState> = _categoryLicenseState

    val listCategoryLicense = mutableListOf<CategoryLicense>()
    private val _categoryQuestionState = mutableStateOf<HomeState>(HomeState.Loading)
    val categoryQuestionState: State<HomeState> = _categoryQuestionState

    fun getListCategoryLicense() {
        viewModelScope.launch {
            categoryMutableStateFlow.value = categoryLicenseDao.getAllCategoryLicense()
        }
    }

    fun saveCategoryLicense(data : String){
        fileStore.saveCategoryLicense(data)
    }
    fun getCategoryLicense(): String {
        return fileStore.getCategoryLicense()
    }

    fun insertAllCategoryLicense() {
        viewModelScope.launch {
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "A1", description = "Xe mô tô 2 bánh có dung tích xi lanh dưới 175 cm3" ))
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "A2", description = "Xe mô tô 2 bánh có dung tíchxi lanh từ 175 cm3 trở lên" ))
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "A3", description = "Xe mô tô 3 bánh, xe 3 gác, xe lam, xích lô..." ))
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "A4", description = "Xe máy léo nhỏ có trọng tải đến 1000kg" ))
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "B1", description = "Ô tô chở người đến 9 chỗ ngồi, Ô tô tải trọng dưới 3,5..." ))
            categoryLicenseDao.insertCategoryLicense(CategoryLicense(nameLicense = "B2", description = "Tương tự B1, dành cho người hành nghề lái xe" ))
        }
    }
    fun insertCategoryQuestion() {
        viewModelScope.launch {
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Tất cả câu lý thuyết"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Câu hỏi điểm liệt "))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Khái niêm quy tắc"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Văn hóa và đạo đức lái xe"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Kỹ thuật lái xe"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Cấu tạo và sửa chữa"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Biển báo đường bộ"))
            categoryQuestionDao.insertCategoryLicense(CategoryQuestion(nameCategoryQuestion = "Sa hình"))

        }
    }

    fun getPercentage(): Int{
        val categoryLicense = fileStore.getCategoryLicense()
        val numberChapter = fileUtils.getNumberTest(categoryLicense)
        if (numberChapter == 0 ) return  0
        var numberPass = 0
        for(i in 0..<numberChapter){
            val isPass = fileStore.getIsPass("${categoryLicense}_IS_PASS_$i")
            if (isPass){
                numberPass++
            }
        }
        println("$numberChapter")
        val percentageChapter = (numberPass*1.0/numberChapter)*100
        return percentageChapter.toFloat().roundToInt()

    }

    fun getAllCategoryLicense(){
        viewModelScope.launch {
            runCatching {
                listCategoryLicense.clear()
                listCategoryLicense.addAll(categoryLicenseDao.getAllCategoryLicense())
                _categoryLicenseState.value = HomeState.Success(listCategoryLicense);

            }.onFailure {
                _categoryLicenseState.value = HomeState.Error(it.message);
            }
        }
    }

    fun getALlCategoryQuestion(){
        viewModelScope.launch {
            runCatching {
                _categoryQuestionState.value = HomeState.Success(categoryQuestionDao.getAllCategoryQuestion())
            }.onFailure {
                _categoryQuestionState.value = HomeState.Error(it.message)
            }
        }
    }

}

sealed class HomeState() {
    data object Loading : HomeState()
    data class Success<out T>(val listCategoryQuestion: T) : HomeState()
    data class Error(val error: String?) : HomeState()
}
