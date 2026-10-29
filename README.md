# PermissionsLab — Practice 8

**Student:** Boymirzayev Akbarshoh Qudratjon o'g'li  
**Group:** Software Engineering  
**Date:** October 2026  

---

## 📌 Project Overview
`PermissionsLab` is an Android application built with **Jetpack Compose** demonstrating the handling of different Android permission categories according to **Lecture 10 — Permissions**.

---

## 📚 Part A: Theory Question Answers

Theory question answers listed below:

### Question 1
* **Four decisions of the permission workflow:**
  1. Can you provide functionality without declaring permissions?
  2. Declare permissions in your app's manifest file.
  3. Is the permission a runtime permission?
  4. Request user to grant permission at runtime.
* **Example without permissions:** Using an Intent (`ACTION_IMAGE_CAPTURE`) to take a photo via an existing camera app.
* **Why it's the best option:** It respects user privacy and eliminates the need to handle complex runtime permission code.

### Question 2
* **Three permission categories:** Install-time, Runtime, and Special permissions.
* **Grant mechanism:**
  * *Install-time:* Granted automatically by the system upon app installation.
  * *Runtime:* Granted via an interactive user dialog while the app is running (introduced in Android 6.0 / API 23).
  * *Special:* Explicitly enabled by the user in the system Settings menu.
* **Code impact:** The category determines whether you need zero code (install-time normal), runtime dialog management and rationale logic (runtime), or intents opening system settings accompanied by lifecycle re-checks (special).

### Question 3
* **Why normal permissions need no runtime check/dialog:** Normal permissions pose minimal risk to user privacy or system operation, so the system grants them automatically at install time once declared in the manifest.
* **Omission consequence:** If omitted from the manifest, attempting to use the API throws a `SecurityException`.
* **Three examples:** `INTERNET`, `ACCESS_NETWORK_STATE`, and `WAKE_LOCK`.

### Question 4
* **Signature permissions:** Granted only to applications signed with the exact same digital certificate as the app declaring the permission.
* **Protection level:** `android:protectionLevel="signature"`.
* **Result for Wallet vs. Stranger app:** Wallet (same certificate) gets the permission automatically without showing a dialog; the stranger app (different certificate) is denied access immediately.

### Question 5
* **When/where shown & granted:** Displayed as a notice on the app store page when viewing app details; granted automatically by the system during app installation.
* **Belonging kinds:** Normal permissions and Signature permissions.

### Question 6
* **Introduction & API 22 behavior:** Runtime permissions were introduced in Android 6.0 (API 23). On API 22 devices, runtime-type permissions are automatically granted at install time with no runtime dialogs shown.
* **Lecture model representation:** Expressed in code as `granted(kind, sdk)`, returning `"at install time"` when `sdk < 23` even for `Kind.RUNTIME`.

### Question 7
* **Runtime permission states in order:** 
  1. *Not asked yet:* (`shouldShowRationale = false`) $\rightarrow$ system dialog pops up.
  2. *Denied once:* (`shouldShowRationale = true`) $\rightarrow$ explain why the feature is needed and ask again.
  3. *Denied twice:* (`shouldShowRationale = false`) $\rightarrow$ no dialog appears anymore.
* **Action after two denials:** Direct the user to the app's **Settings** screen (`Settings.ACTION_APPLICATION_DETAILS_SETTINGS`).

### Question 8
* **Three branches of `rememberPermissionState`:**
  * `isGranted`: Runs when access is active; renders main feature UI.
  * `shouldShowRationale`: Runs after the first denial; renders rationale message and a retry button.
  * `else`: Runs when not yet asked or permanently denied; renders request button.
* **Surviving recomposition:** `rememberPermissionState` safely retains permission observation across recomposition passes[cite: 2].
* **`ExperimentalPermissionsApi`:** Indicates an experimental API subject to future changes, requiring an explicit `@OptIn` annotation[cite: 2].

### Question 9
* **Non-library approach:** Uses `rememberLauncherForActivityResult` combined with `ActivityResultContracts.RequestPermission()`.
* **Functions:** Current state is read via `ContextCompat.checkSelfPermission(...)`, and rationale requirement is checked via `ActivityCompat.shouldShowRequestPermissionRationale(...)`[cite: 2].
* **Comparison:** The Activity Result API is built into AndroidX without external dependencies, while Accompanist offers higher-level Jetpack Compose wrappers[cite: 2].

### Question 10
* **Contract return shape:** `Map<String, Boolean>` mapping permission keys to their granted boolean states.
* **Single interface reason:** Both permissions belong to the same permission group (`LOCATION`), prompting the user in a unified system interface[cite: 2].
* **If user denies both:** The app must degrade functionality gracefully or offer alternative workflows[cite: 2].

### Question 11
* **Special vs. runtime:** Special permissions control powerful device-wide features and require manual enablement inside system settings menus rather than standard pop-up dialogs[cite: 2].
* **Check calls table:**
  * `SYSTEM_ALERT_WINDOW` $\rightarrow$ `Settings.canDrawOverlays(context)`[cite: 2]
  * `WRITE_SETTINGS` $\rightarrow$ `Settings.System.canWrite(context)`[cite: 2]
  * `MANAGE_EXTERNAL_STORAGE` $\rightarrow$ `Environment.isExternalStorageManager()`[cite: 2]
  * `REQUEST_INSTALL_PACKAGES` $\rightarrow$ `packageManager.canRequestPackageInstalls()`[cite: 2]
  * `ACCESS_BACKGROUND_LOCATION` $\rightarrow$ `checkSelfPermission(...)`[cite: 2]

### Question 12
* **Check $\rightarrow$ Settings $\rightarrow$ Check pattern:** Returning from system settings does not trigger a programming callback to your code, so you must re-verify status on return[cite: 2].
* **Fixing with `LifecycleEventEffect`:**
  ```kotlin
  @Composable
  fun OverlayScreen() {
      val context = LocalContext.current
      var canDraw by remember { mutableStateOf(Settings.canDrawOverlays(context)) }
      LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
          canDraw = Settings.canDrawOverlays(context)
      }
      Switch(
          checked = canDraw,
          onCheckedChange = { openOverlaySettings(context) }
      )
  }
