package _2015.d11

import util.Day
import util.listOfMatch
import util.readFullText
import kotlin.system.measureTimeMillis

class Day11(override val input: String) : Day<Long>(input) {
    val rules = arrayOf(::rule1, ::rule2, ::rule3)
    fun rule1(input: String): Boolean {
        for (i in 2 until input.length) {
            if (input[i - 2].toInt() == input[i - 1].toInt() - 1 &&
                input[i - 1].toInt() == input[i].toInt() - 1
            )
                return true
        }

        return false;
    }

    fun rule2(input: String): Boolean {
        return !"iol".any { input.contains(it) }
    }

    fun rule3(input: String): Boolean {
        return Regex("(\\w)\\1{1}").listOfMatch(input).size == 2
    }

    fun nextStr(str: String, i: Int): String {
        val res = str.toMutableList()
        return if (str[i] == 'z') {
            res[i] = 'a'
            nextStr(res.joinToString(""), i - 1)
        } else {
            res[i] = str[i] + 1
            res.joinToString("")
        }
    }

    override fun solve1(): Long {
        var test = input
        var i = 0
        while (!rules.all { it(test) }) {
            test = nextStr(test, test.length - 1)
            i++
        }

        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day11(readFullText("_2015/d11/test"))
    var day = Day11(readFullText("_2015/d11/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day11(readFullText("_2015/d11/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}