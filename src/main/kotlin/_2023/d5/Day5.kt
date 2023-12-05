package _2023.d5

import util.Day
import util.allLong
import util.readFullText
import kotlin.system.measureTimeMillis

class GardenMap(val range: LongRange, val start: Long, val length: Long) {
    fun newPlace(seed: Long): Long = seed - range.first + start
    fun lastPlace(place: Long): Long = place - start + range.first
}

class Garden() {
    val map = mutableListOf<GardenMap>()
    fun nextPlace(seed: Long): Long {
        for (m in map) {
            if (m.range.contains(seed)) {
                return m.newPlace(seed)
            }
        }

        return seed
    }

    fun lastPlace(place: Long): Long {
        for (m in map) {
            if ((m.start..(m.start + m.length)).contains(place)) {
                return m.lastPlace(place)
            }
        }

        return place
    }
}


class Day5(override val input: String) : Day<Long>(input) {
    private var seeds = mutableListOf<LongRange>()
    private val gardens = mutableListOf<Garden>()

    init {
        val data = input.split("\n\n")
        val d = data[0].allLong()

        for (i in d.indices step 2) {
            seeds.add(d[i]..d[i] + d[i + 1])
        }

        (1..7).forEach { gardens.add(dataToGarden(data[it])) }
    }

    private fun dataToGarden(input: String): Garden {
        val g = Garden()

        input.split("\n").drop(1).forEach {
            val data = it.allLong()
            g.map.add(GardenMap(data[1]..data[1] + data[2], data.first(), data[2].toLong()))
        }

        return g
    }

    override fun solve1(): Long = seeds.map { listOf(it.first, it.last - it.first) }.minOf {
        it.minOf {
            var seed = it
            gardens.forEach { garden -> seed = garden.nextPlace(seed) }
            seed
        }
    }.toLong()

    override fun solve2(): Long {
        for (i in 0..100_000_000L) {
            var seed = i
            gardens.reversed().forEach { garden -> seed = garden.lastPlace(seed) }

            if (seeds.any { it.contains(seed) }) {
                return i
            }
        }
        return 0
    }
}

fun main() {
    val day = Day5(readFullText("_2023/d5/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day5(readFullText("_2023/d5/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}