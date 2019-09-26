package com.moducode.daggerexample.dagger

import com.moducode.daggerexample.Presenter
import com.moducode.daggerexample.ui.fragment.EpisodeListPresenter
import dagger.Subcomponent

@Presenter
@Subcomponent(modules = [EpisodeServiceModule::class])
interface PresenterSubcomponent {

    fun buildEpisodeListPresenter(): EpisodeListPresenter

    @Subcomponent.Builder
    interface Builder {
        fun episodeServiceModule(module: EpisodeServiceModule): Builder
        fun build(): PresenterSubcomponent
    }
}