package io.newm.core.ui.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

enum class AppPermission(val manifestName: String) {
    CAMERA(Manifest.permission.CAMERA)
}

fun Context.doWithPermission(
    onGranted: () -> Unit,
    requestPermissionLauncher: RequestPermissionLauncher,
    appPermission: AppPermission
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            appPermission.manifestName
        ) -> {
            onGranted()
        }
        else -> {
            requestPermissionLauncher.launch(appPermission)
        }
    }
}

@JvmInline
value class RequestPermissionLauncher(private val launcher: ManagedActivityResultLauncher<String, Boolean>) {
    fun launch(appPermission: AppPermission) {
        launcher.launch(appPermission.manifestName)
    }
}

@Composable
fun rememberRequestPermissionIntent(onGranted: () -> Unit, onDismiss: () -> Unit) =
    RequestPermissionLauncher(
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onGranted()
            } else {
                onDismiss()
            }
        }
    )