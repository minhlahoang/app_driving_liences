package com.example.appdrivinglience.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.appdrivinglience.core.ReadDataManager
import com.example.appdrivinglience.core.ReadFileUtils
import com.example.appdrivinglience.database.AppDatabase
import com.example.appdrivinglience.database.dao.CategoryLicenseDao
import com.example.appdrivinglience.database.dao.CategoryQuestionDao
import com.example.appdrivinglience.database.dao.ExaminationRandomDao
import com.example.appdrivinglience.database.dao.HistoryDao
import com.example.appdrivinglience.database.dao.NotificationDao
import com.example.appdrivinglience.database.dao.QuestionDao
import com.example.appdrivinglience.database.dao.QuestionWrongDao
import com.example.appdrivinglience.database.dao.TrickDao
import com.example.appdrivinglience.feature.examination_test_screen.CheckExaminationManager
import com.example.appdrivinglience.repository.AlarmRepository
import com.example.appdrivinglience.repository.AlarmRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gplx.db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideCheckExamination() : CheckExaminationManager{
        return  CheckExaminationManager()
    }
    @Provides
    @Singleton
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("APP_DRIVING_LICENSE", Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    fun provideQuestionDao(appDatabase: AppDatabase): QuestionDao {
        return appDatabase.questionDao()
    }
    @Provides
    @Singleton
    fun provideQuestionWrongDao(appDatabase: AppDatabase): QuestionWrongDao {
        return appDatabase.questionWrongDao()
    }

    @Provides
    @Singleton
    fun provideReaderDataManager(@ApplicationContext context: Context) : ReadDataManager {
        return ReadDataManager(context);
    }

    @Provides
    @Singleton
    fun provideTrickDao(appDatabase: AppDatabase): TrickDao {
        return appDatabase.trickDao()
    }
    @Provides
    @Singleton
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }

    @Provides
    @Singleton
    fun provideExaminationRandomDao(appDatabase: AppDatabase): ExaminationRandomDao {
        return appDatabase.examinationRandomDao()
    }
    @Provides
    @Singleton
    fun provideAlarmRepository(@ApplicationContext context: Context): AlarmRepository {
        return AlarmRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun providerReadFileUtils(@ApplicationContext context: Context, trickDao: TrickDao, questionDao: QuestionDao): ReadFileUtils {
        return ReadFileUtils(context = context, trickDao, questionDao)
    }

    @Provides
    @Singleton
    fun provideNotificationDao(appDatabase: AppDatabase): NotificationDao {
        return appDatabase.notificationDao()
    }

    @Provides
    @Singleton
    fun provideCategoryLicenseDao(appDatabase: AppDatabase): CategoryLicenseDao {
        return appDatabase.categoryLicenseDao()
    }

    @Provides
    @Singleton
    fun provideCategoryQuestionDao(appDatabase: AppDatabase): CategoryQuestionDao {
        return appDatabase.categoryQuestionDao()
    }
}