# Specification: Login Feature Migration (Part 2)

## Overview
This track focuses on completing the migration of the Login Feature by moving the `CreateAccount` and `ResetPassword` screens from the `android` module to the `sharedfeatures` module. This ensures the entire authentication flow is compatible with Compose Multiplatform, enabling future cross-platform deployment.

## Functional Requirements
1.  **Create Account Migration:**
    *   Migrate `CreateAccountScreen`, `CreateAccountScreenPresenter`, and `CreateAccountScreenUi` to `sharedfeatures`.
    *   Migrate helper UI components: `EmailVerificationUi`, `EmailAndPasswordUi`.
    *   Migrate state management: `CreateAccountUiState`.
2.  **Reset Password Migration:**
    *   Migrate `ResetPasswordScreen`, `ResetPasswordScreenPresenter`, and `ResetPasswordScreenUi` to `sharedfeatures`.
    *   Migrate state management: `ResetPasswordScreenUiState`, `ResetPasswordUiEvent`.
3.  **Shared Components & Resources:**
    *   Migrate shared sub-components like `TextFieldState`, `TextFieldError`, `Email`, `EmailState`, `Password`, `PasswordState` if they are not already shared.
    *   Migrate strings and drawable resources used by these screens to `compose.resources` in `sharedfeatures`.
4.  **Navigation:**
    *   Update Circuit configuration to use the new shared screens.

## Non-Functional Requirements
-   **Architecture:** Maintain the Circuit architecture (Presenter + UI).
-   **Compatibility:** Ensure the migrated code compiles for all shared targets (Android, iOS, Desktop, Wasm).
-   **Parity:** Zero visual or functional regression on Android.

## Acceptance Criteria
-   [ ] **Create Account:** User can sign up, verify email (if applicable in flow), and land on the main screen.
-   [ ] **Reset Password:** User can request a password reset email.
-   [ ] **Resources:** All text and icons render correctly from shared resources.
-   [ ] **Tests:** Presenter unit tests are migrated/added and passing.
