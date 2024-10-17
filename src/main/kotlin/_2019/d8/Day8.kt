package _2019.d8

import util.*
import kotlin.system.measureNanoTime

class Day8(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val layers =  if (isTest) getLayers(3, 2) else getLayers(25, 6)

    private fun getLayers(col: Int, row: Int): List<Matrix<Int>> =
        input.chunked(col * row).map { it.chunked(col).joinToString("\n") }
            .map { matrixFromString(it, 0) { it.digitToInt() } }

    override fun solve1(): Long = layers.minBy { it.numberOf(0) }.let {
        it.numberOf(1) * it.numberOf(2)
    }.toLong()

    override fun solve2(): Long {
        val image = emptyMatrixOf(layers.first().size, layers.first().nbColumns, 0)

        image.forEachPoint { p ->
            var layer = 0

            while (layers[layer][p] == 2) {
                layer++
            }

            image[p] = layers[layer][p]
        }

        showMap(image, 2, transformation = {
            if (it == 0) {
                ""
            } else it.toString()
        })

        return -1
    }
}

fun main() {
    val day = Day8(false, readFullText("_2019/d8/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day8(true, readFullText("_2019/d8/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}