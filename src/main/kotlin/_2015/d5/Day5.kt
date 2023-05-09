package _2015.d5

import util.Day
import util.readFullText
import java.lang.Exception
import kotlin.system.measureTimeMillis

val VOYELLES = setOf('a', 'e', 'i', 'o', 'u')
val BAD = setOf("ab", "cd", "pq", "xy")

class Day5(override val input: String) : Day<Long>(input) {

    override fun solve1(): Long {
        return input.split("\n").filter { isNice(it) }.size.toLong()
    }

    override fun solve2(): Long {
        return input.split("\n").filter { isNiceV2(it) }.size.toLong()
    }

    fun isNiceV2(line: String): Boolean {
        var doublons = mutableMapOf<String, Int>()
        var xYx = false

        for (i in 0..line.length - 3) {
            if (line[i] == line[i + 2])
                xYx = true
        }

        if (!xYx) return false

        for (i in 0 until line.length - 1) {
            val str = "${line[i]}${line[i + 1]}"

            for (j in i+2..line.length-2) {
                if (str == "${line[j]}${line[j + 1]}")
                    return true
            }
        }

        return false;
    }

    fun isNice(line: String): Boolean {

        BAD.forEach {
            if (line.contains(it))
                return false
        }

        if (line.count { it in VOYELLES } < 3) return false

        var lastChar = line[0]

        for (i in 1 until line.length) {
            if (line[i] == lastChar)
                return true

            lastChar = line[i]
        }

        return false;
    }
}

fun main() {
    //var day = Day5(readFullText("_2015/d5/test"))
    var day = Day5(readFullText("_2015/d5/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day5(readFullText("_2015/d5/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}