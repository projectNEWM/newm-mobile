package io.newm.screens.profile

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface ProfileUiEvent : CircuitUiEvent
sealed interface ProfileEditUiEvent : CircuitUiEvent

/** Profile UI Events */
data object OnDisconnectWallet : ProfileUiEvent
data object OnEditProfile : ProfileUiEvent
data object OnInvestmentPortfolio : ProfileUiEvent
data object OnWalletsScreen : ProfileUiEvent
data class OnConnectWallet(val newmCode: String) : ProfileUiEvent
data object OnVisitRecordStore : ProfileUiEvent
data object OnWalletDialogOpened : ProfileUiEvent

/** Profile Edit UI Events */
data object OnBack : ProfileEditUiEvent
data object OnSaveProfile : ProfileEditUiEvent

/** Profile edit and view UI Events */
data object OnLogout : ProfileEditUiEvent, ProfileUiEvent
data object OnShowTermsAndConditions : ProfileEditUiEvent, ProfileUiEvent
data object OnShowPrivacyPolicy : ProfileEditUiEvent, ProfileUiEvent
data object OnBottomSheetVisible : ProfileEditUiEvent, ProfileUiEvent

