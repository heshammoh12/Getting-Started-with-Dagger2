package com.moducode.daggerexample.dagger

import com.moducode.daggerexample.room.DbRepo
import com.moducode.daggerexample.schedulers.SchedulersBase
import com.moducode.daggerexample.service.EpisodeService
import com.moducode.daggerexample.ui.fragment.EpisodeDetailPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SchedulerModule::class, DatabaseModule::class])
interface AppComponent {

    fun buildEpisodeDetailPresenter(): EpisodeDetailPresenter

    fun presenterSubComponentBuilder(): PresenterSubcomponent.Builder

    fun dbRepo(): DbRepo

    fun schedulers(): SchedulersBase

    fun episodeService(): EpisodeService
}