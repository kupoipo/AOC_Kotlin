package _2019.d6

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Planet(var parent: Planet?, val name: String, var explored: Boolean = false, var state: Int = 0) {
    val orbit = mutableListOf<Planet>()

    fun nbParent(): Int {
        if (parent == null) return 0

        return 1 + parent!!.nbParent()
    }

    override fun toString(): String {
        return "Planet $name"
    }
}

class Day6(override val input: String) : Day<Long>(input) {
    val map = mutableMapOf<String, Planet>()

    init {
        input.split("\n").forEach {
            val firstPlanet = it.split(")")[0]
            val secondPlanet = it.split(")")[1]

            if (!map.containsKey(firstPlanet)) map[firstPlanet] = Planet(null, firstPlanet)
            if (!map.containsKey(secondPlanet)) map[secondPlanet] = Planet(map[firstPlanet], secondPlanet)

            map[firstPlanet]!!.orbit.add(map[secondPlanet]!!)
            if (map[secondPlanet]!!.parent == null) {
                map[secondPlanet]!!.parent = map[firstPlanet]
            }
        }
    }

    override fun solve1(): Long = map.values.sumOf { it.nbParent() }.toLong()

    override fun solve2(): Long {
        val queue = mutableListOf(map["YOU"]!!)
        map["YOU"]!!.explored = true

        while (queue.isNotEmpty()) {
            val planet = queue.removeFirst()

            if (planet.name == "SAN") return (planet.state - 2).toLong()

            val child = mutableListOf(planet.parent)
            child.addAll(planet.orbit)

            child.forEach {
                if (it != null) {
                    if (!it.explored) {
                        it.explored = true
                        it.state = planet.state + 1
                        queue.add(it)
                    }
                }
            }
        }

        return -1
    }
}

fun main() {
    val day = Day6(readFullText("_2019/d6/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day6(readFullText("_2019/d6/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}