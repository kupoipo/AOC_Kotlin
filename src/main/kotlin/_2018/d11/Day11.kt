package _2018.d11

import util.*
import kotlin.system.measureNanoTime

class Day11(override val input: String) : Day<Long>(input) {
    val data = input.toInt()
    val map = emptyMatrixOf(300, 300, 0L)
    val hmReduce = mutableMapOf<Int, Matrix<Long>>()

    init {
        map.forEachPoint {
            val rackID = it.x + 1 + 10
            val powerLevel = rackID * (it.y + 1)
            val adding = powerLevel + data
            val multiply = adding * rackID
            map[it] = ((multiply % 1000) / 100) - 5
        }

        for (i in 2..20) {
            val mapReduce = emptyMatrixOf(300 - (i - 1), 298 - (i - 1), 0L)

            mapReduce.forEachPoint {
                var sum = 0L

                for (y in it.y..it.y + (i - 1)) {
                    for (x in it.x ..it.x + (i-1)) {
                        sum += map[Point(x, y)]
                    }
                }

                mapReduce[it] = sum
            }

            hmReduce[i] = mapReduce
        }
    }

    override fun solve1(): Long {
        val mapReduce = hmReduce[3]!!
        val max = mapReduce.maxOf { it.maxOf { it } }

        mapReduce.forEachPoint {
            if (mapReduce[it] == max) {
                println((it + Point(1, 1)))
            }
        }

        return 0L
    }

    override fun solve2(): Long {
        val maxPerSize = mutableListOf<Pair<Long, String>>()
        for (entry in hmReduce) {
            val mapReduce = entry.value
            val max = mapReduce.maxOf { it.maxOf { it } }
            mapReduce.forEachPoint {
                if (mapReduce[it] == max) {
                    val p = ((it + Point(1, 1)))
                    maxPerSize.add(max to "${p.x},${p.y},   ${entry.key}")
                }
            }
        }
        println(maxPerSize.maxBy { it.first }.second)
        return -1
    }
}

fun main() {
    val day = Day11(readFullText("_2018/d11/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day11(readFullText("_2018/d11/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}