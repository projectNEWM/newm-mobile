package shared

import io.newm.shared.public.models.error.KMMException
import org.koin.core.module.Module
import kotlin.coroutines.cancellation.CancellationException

expect fun platformModule(): Module

expect fun saveFileToDisk(data: ByteArray, filename: String): String

expect fun fileURLForDownloadUrl(downloadURL: String): String

expect fun clearFiles()

expect fun fileExists(url: String): Boolean