package _2023.d5

import util.*
import kotlin.system.measureTimeMillis

class Destination(val range: LongRange, private val start: Long, val length: Long) {
    fun newPlace(seed: Long): Long = seed - range.first + start
    fun transform(localRange: LongRange): LongRange {
        return newPlace(localRange.first)..newPlace(localRange.last)
    }
}

/**
 * Class that represent the transition between two states such as :
 *
 *  fertilizer-to-water map:
 *  49 53 8
 *  0 11 42
 *  42 0 7
 *  57 7 4
 */
class Garden {
    val map = mutableListOf<Destination>()
    fun nextPlace(seed: Long): Long = map.find { it.range.contains(seed) }?.newPlace(seed) ?: seed

    /**
     * Return new ranges location of seeds.
     *
     * Example :
     *  if seeds contains `[[40-57]]`, filteRange will respond with :
     *  [
     *      [29-42],  Second destination which map [11-53] to [0-42]
     *      [49-53]   First destination which map [53-61] to [49-57]
     *  ]
     */
    fun filterRange(seeds: List<LongRange>): MutableList<LongRange> {
        val res = mutableListOf<LongRange>()

        seeds.forEach { seed ->
            val gardensRange = map.filter { it.range.contains(seed) }

            if (gardensRange.isNotEmpty()) {
                res.add(gardensRange.first().transform(seed))
            } else {
                res.add(map.fold(seed) { s, destination ->
                    destination.range.let {
                        if (it.isOverlapping(s)) {
                            res.add(destination.transform(it.overlap(s)))
                            return@fold s.offCut(it.overlap(s))
                        }
                        return@fold s
                    }
                })
            }
        }

        return res
    }

    /**
     * Companion object represent every static function/attribute from a class.
     */
    companion object {
        fun dataToGarden(input: String): Garden = Garden().apply {
            input.split("\n").drop(1).forEach {
                it.allLong().also { data ->
                    map.add(Destination(data[1]..data[1] + data[2], data.first(), data[2]))
                }
            }
        }
    }
}

class Day5(override val input: String) : Day<Long>(input) {
    private val seeds = mutableListOf<LongRange>()
    private val gardens = mutableListOf<Garden>()

    init {
        input.split("\n\n").let { data ->
            data[0].allLong().let { d ->
                d.indices.step(2).forEach { i ->
                    seeds.add(d[i]..d[i] + d[i + 1])
                }
            }

            (data.indices).forEach { gardens.add(Garden.dataToGarden(data[it])) }
        }
    }

    override fun solve1(): Long = seeds.map { listOf(it.first, it.last - it.first) }.flatten().minOf { seed ->
        gardens.fold(seed) { acc, garden -> garden.nextPlace(acc) }
    }

    override fun solve2(): Long = gardens.fold(seeds) { seeds, garden -> garden.filterRange(seeds) }.minOf { it.first }
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