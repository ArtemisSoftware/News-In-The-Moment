package data.logging

import io.ktor.client.plugins.logging.Logger

class HttpLogger() : Logger {
    override fun log(message: String) {
        print(message)
    }
}
