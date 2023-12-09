package util

import java.io.File


fun charToInt(c: Any): Int {
    return if (c is Char) c.digitToInt() else 0
}

fun readFullText(name: String) = File("src/main/kotlin", "$name.txt")
    .readText().replace("\r", "")

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()
