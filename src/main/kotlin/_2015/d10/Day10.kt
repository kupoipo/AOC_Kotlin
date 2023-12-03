package _2015.d10

import util.Day
import util.listOfMatch
import util.readFullText
import util.times
import kotlin.system.measureTimeMillis
class Day10(override val input : String) : Day<Long>(input) {
    fun process(str: String): String = buildString {
            for (match in Regex("""([0-9])\1*""").listOfMatch(str)) {
                append("${match.length}${match.substring(0,1)}")
            }
        }

    override fun solve1(): Long {
        var res = input
        repeat(40) {
            res = process(res)
        }

        return res.length.toLong()
    }
    override fun solve2(): Long {

        var res = input
        repeat(50) {
            res = process(res)
        }

        return res.length.toLong()
    }
}



fun main() {
    //var day = Day10(readFullText("_2015/d10/test"))
    var day = Day10(readFullText("_2015/d10/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day10(readFullText("_2015/d10/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}