package _2024.d4

import util.*
import kotlin.system.measureNanoTime

class Day4(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = matrixFromString(input, ' ') { it }

    private fun isWord(p: Point, dir: Point, word: String): Boolean = word.indices.all {
        val currentPosition = p + (dir * it)
        currentPosition.inMap(map) && map[currentPosition] == word[it]
    }

    /**
     * For every position of [word] first letter, we ensure that the n-th position in a given direction is in the matrix
     * and match the n-th letter of [word].
     *
     * @param word
     * @return A list of (position, direction)
     */
    private fun getPlaces(word: String): List<Pair<Point, Point>> = buildList {
        map.points().filter { map[it] == word.first() }.forEach { position ->
            (-1..1).flatMap { y -> (-1..1).map { x -> Point(x, y) } }.forEach { direction ->
                if (isWord(position, direction, word)) add(position to direction)
            }
        }
    }

    private fun nbXMAS() =
        map.points().sumOf { p ->
            val pos = listOf(Point(0, 0), Point(2, 0), Point(1, 1), Point(0, 2), Point(2, 2))
            listOf("SSAMM", "MMASS", "MSAMS", "SMASM").count { word ->
                (word.indices).all {
                    val position = pos[it] + p
                    position.inMap(map) && word[it] == map[position]
                }
            }
        }

    override fun solve1(): Long = getPlaces("XMAS").size.toLong()
    override fun solve2(): Long = nbXMAS().toLong()
}

fun main() {
    val day = Day4(false, readFullText("_2024/d4/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day4(true, readFullText("_2024/d4/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}