package io.newm.screens.profile.edit

sealed interface ProfileEvent {
    data object OnBack: ProfileEvent
}
