package util

import java.io.File


fun charToInt(c: Any): Int {
    return if (c is Char) c.digitToInt() else 0
}

fun readFullText(name: String) = File("src/main/kotlin", "$name.txt")
    .readText().replace("\r", "")

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()


infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

fun String.allInts(): List<Int> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toInt() }.toList()
}

fun String.allLong(): List<Long> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toLong() }.toList()
}


fun String.allDigits(): List<Int> {
    return Regex("""\d""").listOfMatch(this).map { it.toInt() }
}

fun Regex.listOfMatch(input: String): List<String> {
    return this.findAll(input).toMutableList().map { it.value }
}

fun String.findAllMatch(regex: String): List<String> {
    return Regex(regex).listOfMatch(this)
}

fun Int.toPrimeFactor(): Map<Long, Int> = this.toLong().toPrimeFactor()

fun Long.toPrimeFactor(): Map<Long, Int> {
    val factors = mutableListOf<Long>()
    var divider = 2L
    var nb = this

    while (nb > 1) {
        while (nb % divider == 0L) {
            factors.add(divider)
            nb /= divider
        }
        divider += 1
    }

    return factors.toSet().associateWith { prime -> factors.count { it == prime } }

}

operator fun String.times(i: Int): String {
    var res = ""

    repeat(i) {
        res += this
    }

    return res
}

fun String.allIndexOf(string: String): MutableList<Int> {
    val res = mutableListOf<Int>()
    var index = this.indexOf(string)

    while (index != -1) {
        res.add(index)

        index = this.indexOf(string, index + 1)
    }

    return res;
}