package io.projectnewm.shared.util

actual class Logger actual constructor(
    private val className: String
) {

    actual fun log(msg: String) {
        println("$className: $msg")
    }
}