package _2019.d13

import _2019.IntCode
import util.*
import kotlin.system.measureNanoTime

class Day13(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private var intCode = IntCode(input)
    private val map = mutableMapOf<Point, Long>()

    private fun buildMap(): Matrix<Long> {
        val board: Matrix<Long> = emptyMatrixOf(map.keys.maxOf { it.y + 1 }, map.keys.maxOf { it.x + 1 }, 0)
        map.forEach { (p, o) -> board[p] = o }

        return board
    }

    fun step() = buildList {
        repeat(3) {
            add(intCode.executeUntilOutput())
        }
    }

    override fun solve1(): Long {
        while (intCode.running()) {
            val data = step()
            map[Point(data[0], data[1])] = data[2]
        }
        return buildMap().numberOf(2L).toLong()
    }

    override fun solve2(): Long {
        intCode = IntCode(input, freeInputMode = false, beforeInput = {
            for (i in 1520..1561) {
                intCode.data[i] = 3L
            }
        })

        intCode.data[0] = 2
        var data = step()
        while (intCode.running()) {
            data = step()
            if (data[0] != -1L) map[Point(data[0], data[1])] = data[2]

        }

        return data.last()
    }
}

fun main() {
    val day = Day13(false, readFullText("_2019/d13/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day13(true, readFullText("_2019/d13/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}