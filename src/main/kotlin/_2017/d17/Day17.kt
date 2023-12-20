package _2017.d17

import _2022.d20.li
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day17(override val input: String) : Day<Long>(input) {
    val list = mutableListOf(0L)
    override fun solve1(): Long {
        var i = 1
        (1..2017L).forEach {
            list.add(i, it)
            i = (i + input.toInt()) % list.size + 1
        }

        return list[list.indices.first { list[it] == 2017L } + 1]
    }

    override fun solve2(): Long {
        var i = 1
        var res = 0L
        (1..50_000_000L).forEach {
            i = (i + input.toInt()) % (it.toInt()) + 1
            if (i == 1)
                res = it
        }

        return res
    }
}

fun main() {
    val day = Day17(readFullText("_2017/d17/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day17(readFullText("_2017/d17/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}