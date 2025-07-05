package _2024.d14

import util.*
import kotlin.system.measureNanoTime

class Day14(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    init {
        width = if (isTest) 11L else 101L
        height = if (isTest) 7L else 103L
    }

    class Robot(var position: Point, var velocity: Point) {
        fun move(times: Int) {
            position.x = Math.floorMod(position.x + velocity.x * times, width)
            position.y = Math.floorMod(position.y + velocity.y * times, height)
        }

        fun getQuadrant(): Int {
            if (position.x < width / 2) {
                if (position.y < height / 2) return 0
                else {
                    if (position.y > height / 2) return 1
                }
            } else if (position.x > width / 2) {
                if (position.y < height / 2) return 2
                else {
                    if (position.y > height / 2) return 3
                }
            }

            return 4
        }
    }

    private fun getSafety() =
        robots.groupBy { it.getQuadrant() }.mapValues { it.value.size }.filterKeys { it != 4 }.values.reduce { a, b ->
            a * b
        }.toLong()

    val robots = input.split("\n").map(String::allInts).map { Robot(Point(it[0], it[1]), Point(it[2], it[3])) }

    override fun solve1(): Long {
        robots.forEach { it.move(100) }
        return getSafety()
    }

    override fun solve2(): Long {
        repeat(20000) {
            robots.forEach { it.move(1) }

            val matrix = emptyMatrixOf(height, width, ' ')
            robots.forEach { matrix[it.position] = 'X' }

            if (robots.any {
                    it.position.adjacent().all { it.inMap(matrix) && matrix[it] == 'X' }
                }) {
                showMap(matrix, 1)
                return 101 + it.toLong()
            }
        }

        return -1
    }

    companion object {
        var width: Long = 0
        var height: Long = 0
    }
}

fun main() {
    val day = Day14(false, readFullText("_2024/d14/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day14(true, readFullText("_2024/d14/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}