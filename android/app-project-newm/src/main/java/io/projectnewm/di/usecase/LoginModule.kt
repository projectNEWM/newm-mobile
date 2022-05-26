package io.projectnewm.di.usecase

import io.projectnewm.shared.login.*
import org.koin.dsl.module

val login = module {
    single { LoginConfig.getLoginUseCase() }
}
