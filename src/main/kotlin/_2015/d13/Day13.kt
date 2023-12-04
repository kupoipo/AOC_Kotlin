package _2015.d13

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Day13(override val input: String) : Day<Long>(input) {
    private val neighbors = mutableMapOf<String, MutableMap<String, Int>>()

    private val table = mutableListOf<String>()

    init {
        input.split("\n").forEach {
            val line = it.replace("lose ", "lose -").replace(".", "").split(" ")
            neighbors.getOrPut(line.first()) { mutableMapOf() } [line.last()] = line[3].toInt()
        }
    }

    private fun happinessTable() : Int =
        table.mapIndexed { index, guest ->
            val leftGuest = table[(index - 1 + table.size) % table.size]
            val rightGuest = table[(index + 1 + table.size) % table.size]

            neighbors[guest]?.get(leftGuest)!! + neighbors[guest]?.get(rightGuest)!!
        }.sum()

    fun maxPermut(place : Int) : Int {
        if (place == neighbors.size) return happinessTable()

        var max = 0

        for (n in neighbors.keys) {
            if (table.contains(n)) continue

            table.add(n)

            val happiness = maxPermut(place + 1)
            if (happiness > max)
                max = happiness

            table.removeLast()
        }

        return max
    }

    override fun solve1(): Long {
        return maxPermut(0).toLong()
    }

    override fun solve2(): Long {
        neighbors["you"] = mutableMapOf()

        for (i in neighbors.keys) {
            neighbors["you"]?.set(i, 0)
            neighbors[i]?.set("you", 0)
        }

        return maxPermut(0).toLong()
    }
}

fun main() {
    //var day = Day13(readFullText("_2015/d13/test"))
    var day = Day13(readFullText("_2015/d13/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day13(readFullText("_2015/d13/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}