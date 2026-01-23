# Implementation Plan - Login Feature Migration (Part 2)

## Phase 1: Preparation and Shared Components
- [x] Task: Analyze `CreateAccount` and `ResetPassword` for Android-specific dependencies.
- [x] Task: Migrate required resources (Strings, Drawables) to `sharedfeatures/src/commonMain/composeResources`.
- [x] Task: Migrate shared UI components (`Email`, `Password`, `TextFieldState`, `TextFieldError`) if they are not already properly shared/reused.
    - [ ] Sub-task: Check for duplication with existing `sharedfeatures` components.
    - [ ] Sub-task: Refactor to use common components if duplicates exist.
- [ ] Task: Conductor - User Manual Verification 'Preparation and Shared Components' (Protocol in workflow.md)

## Phase 2: Create Account Screen Migration
- [x] Task: Migrate `CreateAccountScreen` class definition to `sharedfeatures`.
- [x] Task: Migrate `CreateAccountScreenPresenter` to `sharedfeatures`.
    - [x] Sub-task: Update imports to use shared UseCases (`SignupUseCase`).
    - [x] Sub-task: Replace Android-specific implementations with KMP equivalents.
    - [x] Sub-task: Create/Migrate Unit Tests for Presenter.
- [x] Task: Migrate `CreateAccountScreenUi` to `sharedfeatures`.
    - [x] Sub-task: Replace Android resources with `compose.resources`.
    - [x] Sub-task: Ensure `EmailVerificationUi` and `EmailAndPasswordUi` are included/migrated.
- [ ] Task: Verify `CreateAccount` flow on Android emulator.
- [ ] Task: Conductor - User Manual Verification 'Create Account Screen Migration' (Protocol in workflow.md)

## Phase 3: Reset Password Screen Migration
- [x] Task: Migrate `ResetPasswordScreen` class definition to `sharedfeatures`.
- [x] Task: Migrate `ResetPasswordScreenPresenter` to `sharedfeatures`.
    - [x] Sub-task: Update imports to use shared UseCases (`ResetPasswordUseCase`).
    - [x] Sub-task: Create/Migrate Unit Tests for Presenter.
- [x] Task: Migrate `ResetPasswordScreenUi` to `sharedfeatures`.
    - [x] Sub-task: Replace Android resources with `compose.resources`.
- [x] Task: Verify `ResetPassword` flow on Android emulator.
- [ ] Task: Conductor - User Manual Verification 'Reset Password Screen Migration' (Protocol in workflow.md)

## Phase 4: Finalization
- [ ] Task: Update Circuit configuration to register the new shared screens.
- [ ] Task: Remove migrated files from `android/features/login`.
- [ ] Task: Run full regression test on Login, Create Account, and Reset Password flows.
- [ ] Task: Conductor - User Manual Verification 'Finalization' (Protocol in workflow.md)
