package _2015.d9

import util.*
import kotlin.system.measureTimeMillis

enum class MODE {
    MIN, MAX
}

class Day9(override val input : String) : Day<Long>(input) {
    var cities = mutableMapOf<String, Node<String>>()
    var mode: MODE = MODE.MIN
    init {

        input.split("\n").forEach {
            val data = it.replace("= ", "").replace("to ", "").split(" ")
            val city1 = data[0]
            val city2 = data[1]
            val distance = data[2].toInt()

            if (!cities.keys.contains(city1))
                cities[city1] = Node(city1)

            if (!cities.keys.contains(city2))
                cities[city2] = Node(city2)

            cities[city1]!!.addNode(cities[city2]!!, distance)
            cities[city2]!!.addNode(cities[city1]!!, distance)

        }

        cities.keys.forEach {
            println(cities[it])
        }
    }

    fun path(city: Node<String>, citiesVisited : List<String>, length: Int) : Int{
        if (citiesVisited.size == cities.size)
            return length

        var returnLength = if (mode == MODE.MIN) 1_000_000 else 0

        for ( (neighbor, distance) in city.adjacentNodes) {
            if (neighbor.value !in citiesVisited) {
                val localLength = path(neighbor, buildList {
                    citiesVisited.forEach { add(it) }
                    add(neighbor.value)
                }, length + distance)

                if (mode == MODE.MAX) {
                    if (returnLength < localLength)
                        returnLength = localLength
                } else {
                    if (returnLength > localLength)
                        returnLength = localLength
                }
            }
        }

        return returnLength
    }
    fun pathFrom(city: String) : Int {
        return path(cities[city]!!, mutableListOf(city), 0)
    }

    override fun solve1(): Long {
        this.mode = MODE.MIN
        return cities.map { name -> pathFrom(name.key) }.min().toLong()
    }
    override fun solve2(): Long {
        this.mode = MODE.MAX
        return cities.map { name -> pathFrom(name.key) }.max().toLong()
    }
}

fun main() {
    //var day = Day9(readFullText("_2015/d9/test"))
    var day = Day9(readFullText("_2015/d9/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day9(readFullText("_2015/d9/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}