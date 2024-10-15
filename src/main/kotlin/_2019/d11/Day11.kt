package _2019.d11

import _2019.IntCode
import util.*
import kotlin.system.measureNanoTime

class Day11(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val painted = mutableSetOf<Point>()
    val currentPainted = mutableSetOf<Point>()
    var position = Point(0, 0)
    var direction = Direction.UP
    val intCode = IntCode(input, 1, 1)

    fun runOnce() {
        repeat(2) {
            if (intCode.executeUntilOutput() == 1L) {
                painted.add(position)
                currentPainted.add(position)
            } else {
                currentPainted.remove(position)
            }

            direction = if (intCode.executeUntilOutput() == 1L) direction.right() else direction.left()

            position += direction
            intCode.inputInt = if (position in currentPainted) 1L else 0L
        }
    }

    override fun solve1(): Long {
        while (!intCode.halted) {
            runOnce()
        }
        return painted.size.toLong()
    }

    override fun solve2(): Long {
        val map = emptyMatrixOf(currentPainted.maxOf { it.y + 1 },currentPainted.maxOf { it.x + 1 }, ' ')

        currentPainted.forEach { map[it] = 'X'}
        showMap(map)
        return -1
    }
}

fun main() {
    val day = Day11(false, readFullText("_2019/d11/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day11(true, readFullText("_2019/d11/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}