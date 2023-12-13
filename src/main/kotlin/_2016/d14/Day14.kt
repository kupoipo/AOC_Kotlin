package _2016.d14

import util.Day
import util.findAllMatch
import util.md5
import util.readFullText
import kotlin.system.measureNanoTime

class Day14(override val input: String) : Day<Long>(input) {
    val map = mutableMapOf<String, String>()

    private fun isPassword(start: Int, function: (String) -> String): Boolean {
        function(input + start).findAllMatch("""(\w)\1\1""").let {
            if (it.isEmpty()) return false

            val c = it.first().first()

            (start + 1..start + 1000).forEach {
                if (function(input + it).findAllMatch("$c$c$c$c$c").isNotEmpty()) {
                    return true
                }
            }
            return false
        }
    }

    private fun hash2016(s: String): String = map.getOrPut(s) { var start = s; repeat(2017) { start = md5(start) }; start }

    private fun solve(function: (String) -> String): Long {
        var start = 0
        repeat(64) { while (!isPassword(start, function)) start++; start++ }
        return start - 1L
    }

    override fun solve1(): Long = solve(::md5)

    override fun solve2(): Long = solve(::hash2016)
}

fun main() {
    val day = Day14(readFullText("_2016/d14/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day14(readFullText("_2016/d14/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}