package _2018.d9

import util.*
import java.util.*
import kotlin.system.measureNanoTime

class Day9(override val input: String) : Day<Long>(input) {
    private val marbles = LinkedList<Long>()
    private val nbPlayer = MutableList(input.allInts().first()) { 0L }
    private val turns = input.allInts().last()

    private fun turn(turn: Long) {
        if (turn % 23 == 0L) {
            marbles.circle(7)
            nbPlayer[turn % nbPlayer.size] += turn + marbles.removeLast()
            marbles.circle(-1)
        } else {
            marbles.circle(-1)
            marbles.add(turn)
        }
    }

    override fun solve1(): Long {
        marbles.add(0)

        for (turn in 1L..turns) {
            turn(turn)
        }

        return nbPlayer.max().toLong()
    }

    override fun solve2(): Long {
        for (turn in turns+1..turns * 100L) {
            turn(turn)
        }
        return nbPlayer.max().toLong()
    }
}

fun main() {
    val day = Day9(readFullText("_2018/d9/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day9(readFullText("_2018/d9/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}