package _2016.d2

import util.*
import kotlin.system.measureNanoTime

class Day2(override val input: String) : Day<String>(input) {
    val map = matrixOf("123".toMutableList(), "456".toMutableList(), "789".toMutableList())
    val mapTwo = matrixOf(
        mutableListOf('0', '0', '1', '0', '0'),
        mutableListOf('0', '2', '3', '4', '0'),
        mutableListOf('5', '6', '7', '8', '9'),
        mutableListOf('0', 'A', 'B', 'C', '0'),
        mutableListOf('0', '0', 'D', '0', '0')
    )
    val instructions = input.split("\n")
    var currentPosition = Point(1, 1)


    override fun solve1(): String {
        var password = ""

        for (instruction in instructions) {
            for (char in instruction) {
                val direction = Direction.fromChar(char)
                val newPos = currentPosition + direction

                if (newPos.inMap(map)) {
                    currentPosition = newPos
                }
            }

            password += map[currentPosition]
        }

        return password
    }

    override fun solve2(): String {
        var password = ""
        var currentPosition = Point(0, 2)
        for (instruction in instructions) {
            for (char in instruction) {
                val direction = Direction.fromChar(char)
                val newPos = currentPosition + direction

                if (newPos.inMap(mapTwo) && mapTwo[newPos] != '0') {
                    currentPosition = newPos
                }
            }

            password += mapTwo[currentPosition]
        }

        return password
    }
}

fun main() {
    val day = Day2(readFullText("_2016/d2/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day2(readFullText("_2016/d2/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}