package io.projectnewm.shared.util

actual class Logger actual constructor(
    private val className: String
) {

    actual fun log(msg: String) {
        printLogD(className, msg)
    }
}