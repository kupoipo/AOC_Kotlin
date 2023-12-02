package util

import java.io.File
import java.lang.Exception


fun charToInt(c : Any) : Int {
    return if (c is Char) c.digitToInt() else 0
}

fun readFullText(name: String) = File("src/main/kotlin", "$name.txt")
    .readText().replace("\r", "")

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()


infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

fun String.allInts() : List<Int> {
    return """-?\d+""".toRegex().findAll(this).map{ it.value.toInt() }.toList()
}

fun Regex.listOfMatch(input: String): List<String> {
    return this.findAll(input).toMutableList().map { it.value }
}