package io.newm.sharedfeatures.di

import io.newm.shared.config.NewmSharedBuildConfig

interface CommonActivityComponent : CircuitComponent {
    val config: NewmSharedBuildConfig
}
