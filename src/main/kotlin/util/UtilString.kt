package util

import java.lang.Exception

fun String.allInts(): MutableList<Int> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toInt() }.toMutableList()
}

fun String.allLong(): MutableList<Long> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toLong() }.toMutableList()
}

fun String.firstInt(index: Int = 0): Int {
    Regex("""\d+""").find(this, index).let {
        return it?.value?.toInt() ?: -1
    }
}

fun String.isInt() : Boolean {
    return try {
        this.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun String.lastInt(): Int {
    Regex("""\d+""").findAll(this).last().let {
        return it.value.toInt()
    }
}

fun String.rotateCesar(cesar: Int): String {
    var res = ""

    for (c in this) {
        if (c.isLowerCase() || c.isUpperCase()) {

            val start = if (c.isLowerCase()) 'a' else 'A'
            val offset = (c - start + cesar) % 26
            res += (start + offset)
        } else {
            res += c
        }
    }

    return res
}

fun String.allDigits(): MutableList<Int> {
    return Regex("""\d""").listOfMatch(this).map { it.toInt() }.toMutableList()
}

fun Regex.listOfMatch(input: String): List<String> {
    return this.findAll(input).toMutableList().map { it.value }
}

fun String.findAllMatch(regex: String): List<String> {
    return Regex(regex).listOfMatch(this)
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