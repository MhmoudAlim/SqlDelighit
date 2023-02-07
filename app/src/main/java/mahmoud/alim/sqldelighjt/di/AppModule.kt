package mahmoud.alim.sqldelighjt.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mahmoud.alim.sqldelighit.ExampleDataBase
import mahmoud.alim.sqldelighjt.data.datasrc.ExampleDataSource
import mahmoud.alim.sqldelighjt.data.datasrc.ExampleDataSourceImpl
import javax.inject.Singleton

/**
 * @author Mahmoud Alim on 02/02/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = ExampleDataBase.Schema,
            context = app,
            name = "example.db"
        )
    }


    @Provides
    @Singleton
    fun provideDataSource(driver: SqlDriver): ExampleDataSource {
        return ExampleDataSourceImpl(
            ExampleDataBase(driver)
        )
    }
}