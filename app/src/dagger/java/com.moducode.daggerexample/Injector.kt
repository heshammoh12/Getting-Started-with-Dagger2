package com.moducode.daggerexample

import com.moducode.daggerexample.dagger.AppComponent
import com.moducode.daggerexample.dagger.ContextModule
import com.moducode.daggerexample.dagger.DaggerAppComponent
import com.moducode.daggerexample.ui.fragment.EpisodeDetailFragment
import com.moducode.daggerexample.ui.fragment.EpisodeDetailPresenter
import com.moducode.daggerexample.ui.fragment.EpisodeListFragment
import com.moducode.daggerexample.ui.fragment.EpisodeListPresenter
import com.moducode.daggerexample.ui.fragment.contract.EpisodeDetailContract
import com.moducode.daggerexample.ui.fragment.contract.EpisodeListContract
import dagger.internal.DaggerCollections

fun EpisodeDetailFragment.buildPresenter(): EpisodeDetailContract.Actions =
    DaggerAppComponent.builder()
            .contextModule(ContextModule(activity?.applicationContext!!))
            .build()
            .buildEpisodeDetailPresenter()

fun EpisodeListFragment.buildPresenter(): EpisodeListContract.Actions =
    DaggerAppComponent.builder()
            .contextModule(ContextModule(activity?.applicationContext!!))
            .build()
            .buildEpisodeListPresenter()
