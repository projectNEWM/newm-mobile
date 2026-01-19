package io.newm.sharedfeatures

import io.newm.shared.config.NewmSharedBuildConfig

interface CommonActivityComponent : CircuitComponent {
    val config: NewmSharedBuildConfig
}
