package com.dnovaes.csgolive.matches.di

import com.dnovaes.csgolive.common.data.models.DispatcherInterface
import com.dnovaes.csgolive.common.data.remote.PandaScoreAPIInterface
import com.dnovaes.csgolive.matches.summary.data.MatchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MatchesModule {

    @Provides
    fun provideMatchesRepository(
        api: PandaScoreAPIInterface,
        dispatcher: DispatcherInterface,
    ): MatchesRepository = MatchesRepository(
        api,
        dispatcher
    )

}