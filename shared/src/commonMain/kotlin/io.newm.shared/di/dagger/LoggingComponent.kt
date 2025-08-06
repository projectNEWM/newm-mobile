package io.newm.shared.di.dagger

import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import me.tatarka.inject.annotations.Provides

interface LoggingComponent {

    @Provides
    fun provideNewmAppLogger(): NewmAppLogger = NewmAppLogger()

    @Provides
    fun provideNewmAppEventLogger(): NewmAppEventLogger = NewmAppEventLogger()
}