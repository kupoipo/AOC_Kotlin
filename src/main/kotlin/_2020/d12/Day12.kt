
package _2020.d12

import util.*
import kotlin.system.measureNanoTime
class Day12(override val input : String) : Day<Long>(input) {
    private var position = Point(0,0)
    private var facing = Direction.RIGHT
    private val instructions = input.split("\n").map { it.first() to it.firstInt() }

    override fun solve1(): Long {
        for (i in instructions) {
            when (i.first) {
                'L', 'R' -> {
                    repeat(i.second/90) {
                        facing = if (i.first == 'L') facing.left() else facing.right()
                    }
                }

                'F' -> position += (facing * i.second)
                else -> {
                    position = position.moveInDirection(i.first, i.second)
                }
            }
        }

        return position.manhattan()
    }
    override fun solve2(): Long {
        position = Point(0,0)

        var waypoint = Point(10, -1)
        for (i in instructions) {
            when (i.first) {
                'L', 'R' -> {
                    repeat(i.second/90) {
                        if (i.first == 'L') waypoint.rotateLeft() else waypoint.rotateRight()
                    }
                }

                'F' -> position += (waypoint * i.second)
                else -> {
                    waypoint = waypoint.moveInDirection(i.first, i.second)
                }
            }
        }

        return position.manhattan()
    }
}

fun main() {
    val day = Day12(readFullText("_2020/d12/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day12(readFullText("_2020/d12/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}