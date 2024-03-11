package _2018.d5

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day5(override val input : String) : Day<Long>(input) {

    fun react(polymer: String) : Int{
        var currentString = polymer
        var pos = 0

        while (pos < currentString.length - 1) {
            val a = currentString[pos]
            val b = currentString[pos+1]
            if (a != b) {
                if (a.uppercase() == b.uppercase()) {
                    currentString = currentString.substring(0 until pos) + currentString.substring(pos + 2..currentString.lastIndex)
                    pos -= 2
                }
            }

            pos = if (pos+1 < 0) 0 else pos + 1
        }

        return currentString.length
    }

    override fun solve1(): Long {
        return react(input).toLong()
    }
    override fun solve2(): Long {
        return ('a'..'z').minOf { react(input.replace(it.toString(), "").replace(it.uppercase(), "")) }.toLong()
    }
}

fun main() {
    val day = Day5(readFullText("_2018/d5/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day5(readFullText("_2018/d5/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}