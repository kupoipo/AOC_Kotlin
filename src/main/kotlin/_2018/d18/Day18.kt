package _2018.d18

import util.*
import kotlin.system.measureNanoTime

class Day18(override val input: String) : Day<Long>(input) {
    private var map = matrixFromString(input, OPEN) { it }
    private var cycle = mutableListOf<Int>()

    private fun neighbors(p: Point): Map<Char, Int> {
        val res = mutableMapOf<Char, Int>()
        p.forEachEveryNeighbors {
            if (it.outOfMap(map)) return@forEachEveryNeighbors
            res[map[it]] = res.getOrPut(map[it]) { 0 } + 1
        }

        return res
    }

    fun step() {
        val nextMap = emptyMatrixOf(map.size, map[0].size, OPEN)

        nextMap.forEachPoint {
            val neighbors = neighbors(it)

            when (map[it]) {
                OPEN -> {
                    if (neighbors.getOrDefault(TREES, 0) > 2) nextMap[it] = TREES
                }

                TREES -> {
                    if (neighbors.getOrDefault(LUMBERYARD, 0) > 2) nextMap[it] = LUMBERYARD
                    else nextMap[it] = TREES
                }

                else -> {
                    if (neighbors.getOrDefault(LUMBERYARD, 0) > 0 && neighbors.getOrDefault(TREES, 0) > 0) {
                        nextMap[it] = LUMBERYARD
                    }
                }
            }
        }

        map = nextMap
    }

    fun count(c: Char): Long {
        return map.sumOf { line -> line.count { it == c } }.toLong()
    }

    override fun solve1(): Long {
        repeat(10) {
            step()
            cycle.add(map.hashCode())
        }

        return count(TREES) * count(LUMBERYARD)
    }

    override fun solve2(): Long {
        var i = 10
        var max = 1_000_000_000
        var cut = false

        while (i < max) {
            step()

            val sum = map.hashCode()

            if (!cut && cycle.contains(sum)) {
                val sizeCycle = cycle.size - cycle.indexOf(sum)

                i %= sizeCycle
                max %= sizeCycle

                cut = true
            }

            cycle.add(sum)

            i++
        }

        return count(TREES) * count(LUMBERYARD)
    }

    companion object {
        const val OPEN = '.'
        const val TREES = '|'
        const val LUMBERYARD = '#'
    }
}

fun main() {
    val day = Day18(readFullText("_2018/d18/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day18(readFullText("_2018/d18/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}