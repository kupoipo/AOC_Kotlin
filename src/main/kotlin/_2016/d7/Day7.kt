package _2016.d7

import util.Day
import util.findAllMatch
import util.readFullText
import kotlin.system.measureNanoTime

typealias Line = Pair<List<String>, List<String>>

class Day7(override val input: String) : Day<Long>(input) {
    var regex = """([a-z])((?!\1)[a-z])\2\1""" // abba
    var regex2 = """([a-z])((?!\1)[a-z])\1""" // aba

    val lines = input.split("\n").map {
        val openSquareBracket = it.indexOf("[")
        val closeSquareBracket = it.indexOf("]")

        it.findAllMatch("""\w+(?=\[|${'$'})""") to it.findAllMatch("\\[\\w+(?=\\])")
            .map { it.drop(1) }

    }

    fun isTLS(l: Line): Boolean {
        return l.first.any { it.findAllMatch(regex).isNotEmpty() } && l.second.all { it.findAllMatch(regex).isEmpty() }
    }

    override fun solve1(): Long = lines.count { isTLS(it) }.toLong()

    override fun solve2(): Long = lines.count {
        val m = mutableListOf<String>()

        it.first.forEach { str ->
            for (i in 0 until str.length - 2) {
                if (str.substring(i, i + 3).matches(Regex(regex2))) {
                    m.add(str.substring(i, i + 3))
                }
            }
        }

        it.second.any { bracket ->
            m.any { aba ->
                bracket.contains("${aba[1]}${aba[0]}${aba[1]}")
            }
        }
    }.toLong()

}


fun main() {
    val day = Day7(readFullText("_2016/d7/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day7(readFullText("_2016/d7/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}