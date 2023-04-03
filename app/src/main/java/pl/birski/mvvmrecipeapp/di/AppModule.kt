package pl.birski.mvvmrecipeapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.birski.mvvmrecipeapp.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) = app as BaseApplication

    @Singleton
    @Provides
    fun provideRandomString() = "asdasdadfdfgyuy"
}
