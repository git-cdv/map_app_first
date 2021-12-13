package com.chkan.data.di

import android.content.Context
import androidx.room.Room
import com.chkan.data.sources.local.HistoryDao
import com.chkan.data.sources.local.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.sqlcipher.database.SupportFactory


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): HistoryDatabase {
        val passphrase: ByteArray = "password".toByteArray()
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            appContext,
            HistoryDatabase::class.java,
            "history"
        ).openHelperFactory(factory).build()
    }

    @Provides
    fun provideHistoryDao(db: HistoryDatabase): HistoryDao {
        return db.historyDao
    }
}