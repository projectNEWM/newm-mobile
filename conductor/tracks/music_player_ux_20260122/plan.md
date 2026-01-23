# Implementation Plan - Enhance Music Player UI/UX and Performance

## Phase 1: Refactoring and Extraction [checkpoint: ba1a8304]

- [x] Task: Extract Button Components
    - Created `PlayerButtons.kt` and moved all button components there.
    - Updated `MusicPlayerViewer.kt` to import the components.
    - Staged changes.
    - Create `android/features/music-player/src/main/java/io/newm/feature/musicplayer/components/PlayerButtons.kt`
    - Move `PlayButton`, `PauseButton`, `NextTrackButton`, `PreviousTrackButton`, `ShuffleButton`, `RepeatButton` to this new file.
    - Update `MusicPlayerViewer.kt` imports.
    - Stage changes.

- [x] Task: Extract Control Panel
    - Created `PlaybackControlPanel.kt` and moved `PlaybackControlPanel` and `MusicPlayerControls` there.
    - Updated `MusicPlayerViewer.kt` to import the components.
    - Staged changes.
    - Create `android/features/music-player/src/main/java/io/newm/feature/musicplayer/components/PlaybackControlPanel.kt`
    - Move `PlaybackControlPanel` and `MusicPlayerControls` to this new file (or separate files if they are large).
    - Update `MusicPlayerViewer.kt` imports.
    - Stage changes.

- [ ] Task: Conductor - User Manual Verification 'Phase 1: Refactoring' (Protocol in workflow.md)

## Phase 2: UX and Performance Improvements

- [ ] Task: Optimize Palette Generation
    - In `MusicPlayerViewer.kt` (or wherever the logic resides after refactoring), ensure `getPalletColors` resizes the bitmap to a smaller size (e.g., max 100px dimension) before generating the palette.
    - This reduces the computational cost of the `Palette.from(bitmap).generate()` call.
    - Stage changes.

- [ ] Task: Improve Seek Bar Touch Target
    - Modify `MusicPlayerSlider` (or its usage) to have a larger touch target.
    - Use `Modifier.height(48.dp)` (or similar standard touch size) for the layout, but center the `4.dp` visual bar within it, or use `InteractionSource` to expand the hit area.
    - Stage changes.

- [ ] Task: Conductor - User Manual Verification 'Phase 2: UX and Performance' (Protocol in workflow.md)
