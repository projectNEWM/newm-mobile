package io.newm.sharedfeatures.fakes

import io.newm.shared.AppLogger

class FakeAppLogger : AppLogger {
    val errors = mutableListOf<Triple<String, String, Throwable>>()
    val debugs = mutableListOf<Pair<String, String>>()
    val infos = mutableListOf<Pair<String, String>>()

    override fun user(userId: String) {}

    override fun debug(tag: String, message: String) {
        debugs.add(tag to message)
    }

    override fun info(tag: String, message: String) {
        infos.add(tag to message)
    }

    override fun error(tag: String, message: String, exception: Throwable) {
        errors.add(Triple(tag, message, exception))
    }

    override fun breadcrumb(tag: String, message: String) {}
}
