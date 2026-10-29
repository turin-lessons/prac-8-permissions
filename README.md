# PermissionsLab — Practice 8

Student: Boymirzayev Akbarshoh Qudratjon o'g'li  
Group: Software Engineering  
Date: October 2026  

## Project Overview
`PermissionsLab` is an Android application built with **Jetpack Compose** demonstrating the handling of different Android permission categories according to **Lecture 10 — Permissions**[cite: 1, 2].

---

## Implemented Tasks

### Part A: Theory Questions
All theoretical situations and task solutions covering install-time, runtime, special, signature permissions, SDK version models, and auto-reset features have been completed[cite: 1, 2].

### Part B: Hands-on Tasks & Screens

1. **Task 1: Permission Model (`PermissionModel.kt`)**
   - Implemented a standalone Kotlin state-machine tracking permission grants, rationales, and dialog visibility (`dialogShows`) for Android 11+ denial workflows[cite: 1, 2].

2. **Task 2: Normal Permissions (`NetworkStatusScreen.kt`)**
   - Declared `INTERNET` and `ACCESS_NETWORK_STATE` in the manifest[cite: 1, 2].
   - Displays online/offline status via `ConnectivityManager` without runtime dialogs[cite: 1, 2].

3. **Task 3: Runtime Permission & Three States (`CameraPermissionsScreen.kt`)**
   - Declared `CAMERA` in the manifest[cite: 1, 2].
   - Manages the 3 permission states: *Granted*, *Should Show Rationale*, and *Permanently Denied* (after two denials, redirects the user to app settings via `Settings.ACTION_APPLICATION_DETAILS_SETTINGS`)[cite: 1, 2].

4. **Task 4: Special Permission / Overlay (`OverlayPermissionScreen.kt`)**
   - Declared `SYSTEM_ALERT_WINDOW`[cite: 1, 2].
   - Features a Switch UI that sends an intent to system settings[cite: 1, 2].
   - Re-reads overlay status using `LifecycleEventEffect(Lifecycle.Event.ON_RESUME)` upon returning from settings[cite: 2].

5. **Task 5: One-Time Location (`LocationPermissionScreen.kt`)**
   - Requests `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` via `RequestMultiplePermissions`[cite: 1, 2].
   - Classifies UI states into *Precise*, *Approximate*, and *None*.
   - **Observation from Task 5:** When choosing "Only this time", sending the app to the background and returning results in the permission being automatically revoked and cleared by the OS, forcing the UI to update accordingly[cite: 1, 2].

6. **Task 6: Bonus Notification Permission (`NotificationScreen.kt`)**
   - Conditionally requests `POST_NOTIFICATIONS` on API 33+ (Tiramisu) and safely skips the request on lower SDK versions using `Build.VERSION.SDK_INT` checks[cite: 1, 2].

---

## Tested API Levels
- **Tested on Emulator:** API 30 (Android 11) and API 34 (Android 14)[cite: 1].
