package _2023.d5

import util.*
import kotlin.system.measureTimeMillis

class GardenMap(val range: LongRange, private val start: Long, val length: Long) {
    fun newPlace(seed: Long): Long = seed - range.first + start
    fun transform(localRange: LongRange): LongRange {
        return newPlace(localRange.first)..newPlace(localRange.last)
    }
}

class Garden {
    val map = mutableListOf<GardenMap>()
    fun nextPlace(seed: Long): Long {
        for (m in map) {
            if (m.range.contains(seed)) {
                return m.newPlace(seed)
            }
        }

        return seed
    }

    fun filterRange(seeds: List<LongRange>): MutableList<LongRange> {
        val res = mutableListOf<LongRange>()

        for (seed in seeds) {
            var localRange = seed

            var add = true
            for (gardenRange in this.map) {

                if (gardenRange.range.contains(localRange)) {
                    res.add(gardenRange.transform(localRange))
                    add = false
                    break
                }

                if (gardenRange.range.overlap(localRange)) {
                    val toTransform = gardenRange.range.getOverlapped(localRange)
                    res.add(gardenRange.transform(toTransform))

                    localRange = if (localRange.last == toTransform.last) localRange.first..toTransform.first
                    else toTransform.last..localRange.last
                }
            }

            if (add) res.add(localRange)
        }

        return res
    }
}


class Day5(override val input: String) : Day<Long>(input) {
    private val seeds = mutableListOf<LongRange>()
    private val gardens = mutableListOf<Garden>()

    init {
        val data = input.split("\n\n")

        data[0].allLong().let { d ->
            d.indices.step(2).forEach { i ->
                seeds.add(d[i]..d[i] + d[i + 1])
            }
        }

        (1..7).forEach { gardens.add(dataToGarden(data[it])) }
    }

    private fun dataToGarden(input: String): Garden = Garden().apply {
        input.split("\n").drop(1).forEach {
            val data = it.allLong()
            map.add(GardenMap(data[1]..data[1] + data[2], data.first(), data[2]))
        }
    }


    override fun solve1(): Long = seeds.map { listOf(it.first, it.last - it.first) }.minOf {
        it.minOf { seed -> gardens.fold(seed) { acc, garden -> garden.nextPlace(acc) } }
    }

    override fun solve2(): Long =
        gardens.fold(seeds) { seeds, garden -> garden.filterRange(seeds) }.minOf { it.first }

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