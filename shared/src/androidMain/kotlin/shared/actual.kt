package shared

import com.liftric.kvault.KVault
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.engine.android.Android
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.db.NewmDatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(NewmDatabase.Schema, get(), "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Android.create() }
    single { KVault(get()) }
}

actual fun saveFileToDisk(data: ByteArray, filename: String): String {
    return ""
}

actual fun fileURLForDownloadUrl(downloadURL: String): String {
    return ""// documentsDirectory.URLByAppendingPathComponent(downloadURL.last)
}

actual fun clearFiles() {
//    val fileManager = NSFileManager.defaultManager
//    val urls = fileManager.contentsOfDirectoryAtURL(documentsDirectory, null, null)
//    val enumerator = urls.objectEnumerator()
//    var url: NSURL?
//    while (enumerator.nextObject() != null) {
//        url = enumerator.nextObject() as NSURL
//        if (url != null) {
//            fileManager.removeItemAtURL(url, null)
//        }
//    }
}

actual fun fileExists(url: String): Boolean {
    return true
//    return NSFileManager.defaultManager.fileExistsAtPath(fileURLForDownloadUrl(url).encodeURLPath())
}