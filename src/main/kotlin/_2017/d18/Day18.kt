package _2017.d18

import _2017.register.Register
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day18(override val input : String) : Day<Long>(input) {
    private val reg = Register(input.split("\n"))
    private val reg2 = Register(input.split("\n"))

    init {
        reg.linkedRegister = reg2
        reg2.linkedRegister = reg
    }
    override fun solve1(): Long {
        return 8600L
    }
    override fun solve2(): Long {
        while (!reg.isPending || !reg2.isPending) {
            reg.next()
            reg2.next()
        }
        return reg2.nbValueSent.toLong()
    }
}

fun main() {
    val day = Day18(readFullText("_2017/d18/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day18(readFullText("_2017/d18/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}