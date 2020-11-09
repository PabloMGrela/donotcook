package com.grela.clean.remote

import java.io.IOException

object ServerFixtures {

    const val SUCCESS_RESPONSE = "getRestaurants.json"

    @Throws(IOException::class)
    fun enqueueServerFile(server: MockServer, file: String) {
        server.enqueueFile(file)
    }

    fun enqueueServerError(server: MockServer, code: Int) {
        server.enqueue(code)
    }
}