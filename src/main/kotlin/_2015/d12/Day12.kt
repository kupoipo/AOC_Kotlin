package _2015.d12

import util.Day
import util.JSON
import util.JSONParser
import util.readFullText
import kotlin.system.measureTimeMillis

class Day12(override val input: String) : Day<Long>(input) {

    private fun sommeNumericFromJSON(json: JSON, countRed: Boolean): Long {
        if (!countRed && json.properties.values.contains("red")) return 0

        return json.properties.values.sumOf {
            if (it is JSON) return@sumOf sommeNumericFromJSON(it, countRed)
            if (it is List<*>) return@sumOf sommeNumericFromList(it, countRed)
            if (it.toString().matches("""-?\d+""".toRegex())) return@sumOf it.toString().toLong()
            0
        }
    }


    private fun sommeNumericFromList(list: List<*>, countRed: Boolean): Long = list.sumOf {
        if (it is JSON) return@sumOf sommeNumericFromJSON(it, countRed)
        if (it is List<*>) return@sumOf sommeNumericFromList(it, countRed)
        if (it.toString().matches("""-?\d+""".toRegex())) return@sumOf it.toString().toLong()
        0
    }

    override fun solve1(): Long = sommeNumericFromJSON(JSONParser(input).parse(), true)
    override fun solve2(): Long = sommeNumericFromJSON(JSONParser(input).parse(), false)

}

fun main() {
    //var day = Day12(readFullText("_2015/d12/test"))
    var day = Day12(readFullText("_2015/d12/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day12(readFullText("_2015/d12/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}