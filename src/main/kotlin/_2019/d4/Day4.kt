package _2019.d4

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis


class Day4(override val input : String) : Day<Long>(input) {
    private val range = 264360.. 746325

    private fun isPassword(password: Int) : Boolean {
        var hasDouble = false
        var n = password%10
        var newPassword = password/10

        while (newPassword > 0) {
            val n2 = newPassword%10

            if (n2 > n) return false

            if (n2 == n) hasDouble = true
            n = n2
            newPassword /= 10
        }

        return hasDouble
    }

    private fun hasNoTriple(password: Int): Boolean {
        val passwordStr = password.toString()
        val map = mutableMapOf<Char, Int>()

        var count = 1

        for (i in 1 until passwordStr.length) {
            if (passwordStr[i] == passwordStr[i - 1]) {
                count++
            } else {
                map[passwordStr[i-1]] = count
                count = 1
            }
        }

        map[passwordStr.last()] = count

        return map.values.contains(2)
    }

    override fun solve1(): Long = range.count{ isPassword(it) }.toLong()
    override fun solve2(): Long = range.filter{isPassword(it)}.count{ hasNoTriple(it) }.toLong()
}

fun main() {
    //var day = Day4(readFullText("_2019/d4/test"))
    var day = Day4(readFullText("_2019/d4/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day4(readFullText("_2019/d4/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}