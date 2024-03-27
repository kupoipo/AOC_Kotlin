package _2020.d2

import util.Day
import util.allLong
import util.readFullText
import java.lang.Exception
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day2(override val input: String) : Day<Long>(input) {
    data class Line(val range: LongRange, private val letter: Char, private val password: String) {
        fun isValidV1() = range.contains(password.count { it == letter }.toLong())
        fun isValidV2() = (password[range.first.toInt() - 1] == letter) xor (password[range.last.toInt() - 1] == letter)
    }

    private val passwords = input.split("\n").map { line ->
        Line(
            line.allLong().let { it.first()..abs(it.last()) },
            line[line.indexOf(" ") + 1],
            line.substring(line.indexOf(":") + 1).trim()
        )
    }

    override fun solve1(): Long = passwords.count { it.isValidV1() }.toLong()

    override fun solve2(): Long = passwords.count { it.isValidV2() }.toLong()
}

fun main() {
    val day = Day2(readFullText("_2020/d2/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day2(readFullText("_2020/d2/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}