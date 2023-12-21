package _2017.d8

import _2017.register.Register
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day8(override val input : String) : Day<Long>(input) {
    val reg = Register(input.split("\n"))
    var max = 0L
    override fun solve1(): Long {
        while (reg.hasNext()) {
            reg.next()
            reg.register.maxOf { localValue -> localValue.value }.let { maxLocal ->
                if (maxLocal > max) {
                    max = maxLocal
                }
            }
        }

        return reg.register.maxOf { it.value }
    }
    override fun solve2(): Long {
        return max
    }
}

fun main() {
    val day = Day8(readFullText("_2017/d8/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day8(readFullText("_2017/d8/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}