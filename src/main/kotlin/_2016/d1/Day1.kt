package _2016.d1

import util.*
import kotlin.system.measureNanoTime

class Day1(override val input: String) : Day<Long>(input) {
    val start = Point(0, 0)
    val instructions = input.findAllMatch("""(R|L)\d+""")

    fun nextDirection(dir: Char, current: Direction): Direction {
        return if (dir == 'R') {
            when (current) {
                Direction.LEFT -> Direction.UP
                Direction.UP -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                else -> Direction.LEFT
            }
        } else {
            when (current) {
                Direction.LEFT -> Direction.DOWN
                Direction.UP -> Direction.LEFT
                Direction.RIGHT -> Direction.UP
                else -> Direction.RIGHT
            }
        }
    }

    override fun solve1(): Long {
        var currentDirection = Direction.UP
        var currentPos = start

        for (it in instructions){
            val dir = it.first()
            val step = it.substring(1).toInt()

            currentDirection = nextDirection(dir, currentDirection)

            currentPos += currentDirection.times(step)
        }

        return currentPos.manhattan(start).toLong()
    }

    override fun solve2(): Long {
        var currentDirection = Direction.UP
        var currentPos = start
        val positions = mutableSetOf(currentPos)

        instructions.forEach {
            val dir = it.first()
            val step = it.substring(1).toInt()

            currentDirection = nextDirection(dir, currentDirection)

            repeat(step) {
                currentPos += currentDirection

                if (currentPos in positions) return currentPos.manhattan(start).toLong()

                positions.add(currentPos)
            }
        }

        return currentPos.manhattan(start).toLong()
    }
}

fun main() {
    val day = Day1(readFullText("_2016/d1/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day1(readFullText("_2016/d1/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}