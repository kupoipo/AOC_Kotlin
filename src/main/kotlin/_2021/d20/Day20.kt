package _2021.d20

import _2021.d18.split
import _2022.d20.li
import util.*
import kotlin.system.measureNanoTime


class Day20(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private var transform: String = input.split("\n\n").first().replace("\n", "")
    private var outside = '.'
    private var lightPixel = matrixFromString(input.split("\n\n").last(), '.') { it }

    private fun getBinary(pos: Point) = buildString {
        pos.forEachEveryNeighbors(true) {
            if (it.inMap(lightPixel)) append(lightPixel[it])
            else append(outside)
        }
    }.replace(".", "0").replace("#", "1")

    fun turn() {
        lightPixel.forEach {
            it.add(0, outside)
            it.add(outside)
        }
        lightPixel.add(0, MutableList(lightPixel.nbColumns) { outside })
        lightPixel.add(MutableList(lightPixel.nbColumns) { outside })

        val nextMatrix = emptyMatrixOf(lightPixel.size, lightPixel.nbColumns, '.')

        lightPixel.forEachPoint {
            val binaryString = getBinary(it)
            val binary = binaryString.toInt(2)
            nextMatrix[it] = transform[binary]
        }

        lightPixel = nextMatrix
        if (!isTest)
            outside = if (outside == '.') '#' else '.'
    }

    override fun solve1(): Long {
        repeat(2) {
            turn()
        }
        return lightPixel.sumOf { it.count { it == '#' } }.toLong()
    }

    override fun solve2(): Long {
        repeat(48) {
            turn()
        }
        return lightPixel.sumOf { it.count { it == '#' } }.toLong()
    }
}

fun main() {
    val day = Day20(false, readFullText("_2021/d20/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day20(true, readFullText("_2021/d20/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}