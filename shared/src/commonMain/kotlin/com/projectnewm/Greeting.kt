package com.projectnewm

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}