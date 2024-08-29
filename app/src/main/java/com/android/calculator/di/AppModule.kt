package com.android.calculator.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.calculator.feature.calculatormain.data.repository.BookmarkRepositoryImpl
import com.android.calculator.feature.calculatormain.data.repository.CalculationRepositoryImpl
import com.android.calculator.feature.calculatormain.data.source.CalculatorDatabase
import com.android.calculator.feature.calculatormain.domain.repository.BookmarkRepository
import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository
import com.android.calculator.feature.calculatormain.domain.usecase.BookmarkUseCases
import com.android.calculator.feature.calculatormain.domain.usecase.CalculationUseCases
import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.DeleteSelectedBookmarksUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.GetBookmarksUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.InsertBookmarkUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.DeleteSelectedCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.GetCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.InsertCalculationUseCase
import com.android.calculator.feature.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.android.calculator.feature.currencyconverter.data.source.CurrencyRatePreference
import com.android.calculator.feature.discountcalculator.data.repository.DiscountCalculatorRepositoryImpl
import com.android.calculator.feature.discountcalculator.data.source.DiscountCalculatorPreferences
import com.android.calculator.feature.lenghtconverter.data.repository.LengthConverterRepositoryImpl
import com.android.calculator.feature.lenghtconverter.data.source.LengthPreference
import com.android.calculator.feature.massconverter.data.repository.MassConverterRepositoryImpl
import com.android.calculator.feature.massconverter.data.source.MassPreference
import com.android.calculator.feature.numeralsystem.data.repository.NumeralSystemRepositoryImpl
import com.android.calculator.feature.numeralsystem.data.source.NumeralSystemPreferences
import com.android.calculator.feature.settings.data.repository.SettingsRepositoryImpl
import com.android.calculator.feature.settings.data.source.SettingsPreferences
import com.android.calculator.feature.tipcalculator.data.repository.TipCalculatorRepositoryImpl
import com.android.calculator.feature.tipcalculator.data.source.TipCalculatorPreferences
import com.android.calculator.feature.tipcalculator.domain.repository.TipCalculatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCalculatorDatabase(app: Application): CalculatorDatabase {
        return Room.databaseBuilder(
            app,
            CalculatorDatabase::class.java,
            CalculatorDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCalculationRepository(db: CalculatorDatabase): CalculationRepository {
        return CalculationRepositoryImpl(db.calculationDao)
    }

    @Provides
    @Singleton
    fun provideCalculationUseCases(
        calculationRepository: CalculationRepository,
        bookmarkRepository: BookmarkRepository,
    ): CalculationUseCases {
        return CalculationUseCases(
            insertCalculation = InsertCalculationUseCase(calculationRepository),
            getCalculations = GetCalculationsUseCase(calculationRepository),
            deleteSelectedCalculations = DeleteSelectedCalculationsUseCase(calculationRepository),
            insertBookmark = InsertBookmarkUseCase(bookmarkRepository)
        )
    }

    @Provides
    @Singleton
    fun provideLengthPreferences(@ApplicationContext context: Context): LengthPreference {
        return LengthPreference(context)
    }

    @Provides
    @Singleton
    fun provideLengthConverterRepository(
        preferences: LengthPreference
    ): LengthConverterRepositoryImpl {
        return LengthConverterRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideMassPreferences(@ApplicationContext context: Context): MassPreference {
        return MassPreference(context)
    }

    @Provides
    @Singleton
    fun provideMasConverterRepository(
        preferences: MassPreference
    ): MassConverterRepositoryImpl {
        return MassConverterRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideNumeralSystemPreferences(@ApplicationContext context: Context): NumeralSystemPreferences {
        return NumeralSystemPreferences(context)
    }

    @Provides
    @Singleton
    fun provideNumeralSystemRepository(
        preferences: NumeralSystemPreferences
    ): NumeralSystemRepositoryImpl {
        return NumeralSystemRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideDiscountPreferences(@ApplicationContext context: Context): DiscountCalculatorPreferences {
        return DiscountCalculatorPreferences(context)
    }

    @Provides
    @Singleton
    fun provideDiscountRepositoryImpl(
        preferences: DiscountCalculatorPreferences
    ): DiscountCalculatorRepositoryImpl {
        return DiscountCalculatorRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideCurrencyPreferences(@ApplicationContext context: Context): CurrencyRatePreference {
        return CurrencyRatePreference(context)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepositoryImpl(
        preferences: CurrencyRatePreference
    ): CurrencyRepositoryImpl {
        return CurrencyRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideSettingsPreferences(@ApplicationContext context: Context): SettingsPreferences {
        return SettingsPreferences(context)
    }

    @Provides
    @Singleton
    fun provideSettingsRepositoryImpl(
        preferences: SettingsPreferences
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideTipCalculatorPreferences(@ApplicationContext context: Context): TipCalculatorPreferences {
        return TipCalculatorPreferences(context)
    }

    @Provides
    @Singleton
    fun provideTipCalculatorRepository(
        preferences: TipCalculatorPreferences
    ): TipCalculatorRepository {
        return TipCalculatorRepositoryImpl(preferences)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface SettingsRepositoryEntryPoint {
        fun settingsRepository(): SettingsRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(db: CalculatorDatabase): BookmarkRepository {
        return BookmarkRepositoryImpl(db.bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideBookmarkUseCases(repository: BookmarkRepository): BookmarkUseCases {
        return BookmarkUseCases(
            insertBookmark = InsertBookmarkUseCase(repository),
            getBookmarks = GetBookmarksUseCase(repository),
            deleteSelectedBookmarks = DeleteSelectedBookmarksUseCase(repository)
        )
    }
}