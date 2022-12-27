package _2021.d11

import util.*
import kotlin.system.measureTimeMillis

class Octopus(var nb : Int) {
    val neighbors = mutableSetOf<Octopus>()
    var flashed = false

    override fun toString(): String {
        return nb.toString()
    }

    fun increase() : Set<Octopus> {
        if (flashed) return setOf()

        nb += 1

        if (nb >= 10) {
            nb = 0
            flashed = true
            neighbors.forEach { _ -> increase() }
            return neighbors
        }

        return setOf()
    }
}

fun charToOctopus(c : Any) : Octopus {
    return if (c is Char) Octopus(c.digitToInt()) else Octopus(0)
}

class Day11(override val input : String) : Day<Long>(input) {
    val map : Matrix<Octopus> = matrixFromString(input, Octopus(0), ::charToOctopus)
    val octopus : Set<Octopus>

    init {
        map.forEachPoint { p ->
            p.forEachEveryNeighbors { neighbor ->
                if (neighbor.inMap(map))
                    map[p].neighbors.add(map[neighbor])
            }
        }

        octopus = buildSet { map.forEachPoint { add(map[it]) } }
    }

    private fun turn() {
        val toTest = octopus.toMutableList()

        octopus.forEach { it.flashed = false }

        while (toTest.isNotEmpty()) {

            val current = toTest.removeFirst()
            val newToTest = current.increase().filter { !it.flashed }

            toTest.addAll(newToTest)
        }
    }

    override fun solve1(): Long =(1..100).map { turn(); map.sumOf { it.sumOf { if (it.flashed) 1L else 0 } }}.sum()

    override fun solve2(): Long {
        repeat(10000) {
            turn()

            if (map.sumOf { it.sumOf { if (it.nb == 0) 1L else 0 } } == (map.size * map[0].size).toLong()) {
                return it.toLong() + 101
            }
        }

        return 0L
    }
}

fun main() {
    //var day = Day11(readFullText("_2021/d11/test"))
    var day = Day11(readFullText("_2021/d11/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}