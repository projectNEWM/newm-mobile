package io.newm.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

suspend fun ImageBitmap.toTempFile(context: Context): File =
    withContext(Dispatchers.IO) {
        val file = File.createTempFile("img", ".png", context.cacheDir)
        val bitmap = asAndroidBitmap()
        FileOutputStream(file).use { output ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        }
        file
    }
