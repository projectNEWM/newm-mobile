# Track Specification: Enhance Music Player UI/UX and Performance

## Goal
Improve the user experience and performance of the Music Player screen by optimizing background color extraction, enhancing touch targets for the seek bar, and refactoring the monolithic `MusicPlayerViewer.kt` file.

## Requirements

### Performance
- **Optimize Palette Generation:** The background color is derived from the album art. Ensure this process is efficient, potentially by using a downscaled bitmap for the palette calculation to reduce CPU usage and delay.

### User Experience (UX)
- **Seek Bar Touch Target:** The current seek bar (`MusicPlayerSlider`) has a visual height of `4.dp`, which may be too small for reliable touch interaction. Increase the touch target size without necessarily changing the visual design (or make the visual design adapt to interaction).
- **Layout Refinement:** Review and improve the visual hierarchy of the `PlaybackControlPanel`.

### Code Quality (Refactoring)
- **Component Extraction:** Decompose `MusicPlayerViewer.kt` into smaller, manageable components.
    - Extract `MusicPlayerControls`
    - Extract `PlaybackControlPanel`
    - Extract individual buttons (Play, Pause, Next, Previous, Shuffle, Repeat) into their own files or a dedicated `controls` package.

## detailed Design
- **Background Color:** Continue using `Palette` API but ensure the input bitmap is resized to a small dimension (e.g., 100x100) before processing.
- **Slider:** Use a custom implementation or a wrapper around the slider to increase the hit test area.

## Verification
- **Snapshot Tests:** Run existing Paparazzi tests to ensure no regression in visual appearance (unless intended).
- **Manual Verification:** Verify smooth animations and easier seek bar interaction on a device/emulator.
