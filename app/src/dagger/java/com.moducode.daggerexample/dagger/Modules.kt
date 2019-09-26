package com.moducode.daggerexample.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.moducode.daggerexample.Presenter
import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.room.DbRepoImpl
import com.moducode.daggerexample.room.EpisodeDB
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.schedulers.SchedulersImpl
import com.moducode.daggerexample.service.EpisodeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Singleton
    @Provides
    fun provideSchedulers(): SchedulersBase = SchedulersImpl()
}

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): DbRepo =
            DbRepoImpl(Room.databaseBuilder(context, EpisodeDB::class.java, "db-episode").build())
}

@Module(includes = [RetrofitModule::class])
class EpisodeServiceModule {

    @Presenter
    @Provides
    fun provideEpisodeService(retrofit: Retrofit): EpisodeService = retrofit.create(EpisodeService::class.java)
}

class RetrofitModule {

    @Presenter
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, moshi: MoshiConverterFactory, callAdapterFactory: RxJava2CallAdapterFactory): Retrofit =
            Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(callAdapterFactory)
                    .baseUrl("http://api.tvmaze.com/")
                    .addConverterFactory(moshi)
                    .build()

    @Presenter
    @Provides
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()

    @Presenter
    @Provides
    fun provideInterceptor(): Interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d(it) })
            .apply { level = HttpLoggingInterceptor.Level.BASIC }

    @Presenter
    @Provides
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5*5*1024)

    @Presenter
    @Provides
    fun provideRxCallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Presenter
    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
}

@Module
class ContextModule(val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

}