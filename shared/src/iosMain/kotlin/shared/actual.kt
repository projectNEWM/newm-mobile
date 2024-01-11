package shared

import com.liftric.kvault.KVault
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.engine.darwin.Darwin
import io.ktor.http.encodeURLPath
import io.newm.shared.db.cache.NewmDatabase
import io.newm.shared.internal.db.NewmDatabaseWrapper
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import org.koin.dsl.module
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.create
import platform.Foundation.lastPathComponent
import platform.Foundation.writeToFile

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(NewmDatabase.Schema, "newm.db")
        NewmDatabaseWrapper(NewmDatabase(driver))
    }
    single { Darwin.create() }
    single { KVault() }
}

actual fun saveFileToDisk(data: ByteArray, filename: String): String {
    val fileManager = NSFileManager.defaultManager
    val fileURL = documentsDirectory.URLByAppendingPathComponent(filename)!!
    fileManager.createFileAtPath(fileURL.absoluteString!!, null, null)
    data.toNSData().writeToFile(fileURL.absoluteString!!, true)
    return fileURL.absoluteString!!
}

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData() : NSData = memScoped {
    NSData.create(bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong())
}

//@OptIn(ExperimentalForeignApi::class)
//fun ByteArray.toNSData(): NSData {
//    return NSData.dataWithBytes(this.refTo(0).getPointer(this.size), this.size.toULong())!!
//}

actual fun fileURLForDownloadUrl(downloadURL: String): String {
    val downloadURL_url = NSURL.URLWithString(downloadURL)!!
    return documentsDirectory.URLByAppendingPathComponent(downloadURL_url.lastPathComponent!!)!!.absoluteString!!
}

@OptIn(ExperimentalForeignApi::class)
actual fun clearFiles() {
    val fileManager = NSFileManager.defaultManager
    val urls = fileManager.contentsOfDirectoryAtURL(documentsDirectory, null, 0u, error = null)!! as List<NSURL>
    for (url in urls) {
        fileManager.removeItemAtURL(url, null)
    }
}

actual fun fileExists(url: String): Boolean {
    return NSFileManager.defaultManager.fileExistsAtPath(fileURLForDownloadUrl(url).encodeURLPath())
}

val documentsDirectory: NSURL
    get() = NSFileManager.defaultManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).last() as NSURL