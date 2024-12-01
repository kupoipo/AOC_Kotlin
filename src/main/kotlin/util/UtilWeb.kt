package util

import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object UtilWeb {
    val SESSION = File("src/main/kotlin/.session").readLines().first()
    fun submit(day: Int, year: Int, submit: String): String {
        val connection = HttpRequest.newBuilder().uri(URI.create("https://adventofcode.com/$year/day/$day/answer"))
            .setHeader(
                "Cookie",
                "session=$SESSION")
            .POST(HttpRequest.BodyPublishers.ofString("""
{
    "answer": $submit,
    "level": 1
}
        """.trimIndent())).build()

        val response = HttpClient.newBuilder().build().send(connection, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun getInput(day: Int, year: Int): String {
        val url = URL("https://adventofcode.com/$year/day/$day/input")
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty(
            "Cookie",
            "session=$SESSION"
        )

        connection.connect()
        return connection.inputStream.reader().readLines().joinToString("")
    }
}

fun main() {
    UtilWeb.getInput(1, 2023)
    println(UtilWeb.submit(1, 2023, "tst"))
}