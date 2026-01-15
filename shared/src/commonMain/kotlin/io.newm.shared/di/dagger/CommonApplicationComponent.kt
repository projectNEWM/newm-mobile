package io.newm.shared.di.dagger

import shared.OSDependencyProvider

interface CommonApplicationComponent:
    NetworkComponent,
    LoggingComponent,
    NetworkServiceComponent,
    StorageComponent,
    UseCaseComponent,
    OSDependencyProvider