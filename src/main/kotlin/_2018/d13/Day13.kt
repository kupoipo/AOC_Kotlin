package _2018.d13

import util.*
import kotlin.system.measureNanoTime

class SpaceShip(var position: Point, var direction: Direction, private var rotation: Int = 0, var isDead: Boolean = false) {
    fun step(map: Matrix<Char>) {
        position += direction

        when (map[position]) {
            '\\' -> {
                direction = when (direction) {
                    Direction.UP -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                    else -> Direction.RIGHT
                }
            }

            '/' -> {
                direction = when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.LEFT -> Direction.DOWN
                    Direction.RIGHT -> Direction.UP
                    else -> Direction.LEFT
                }
            }

            '+' -> {
                when(rotation) {
                    0 -> direction = direction.left()
                    2 -> direction = direction.right()
                }

                rotation = (rotation+1) % 3
            }
        }
    }

    override fun toString(): String {
        return "$position - $direction"
    }
}

class Day13(override val input: String) : Day<Long>(input) {
    private val spaceShip = mutableListOf<SpaceShip>()
    private val intersections = mutableMapOf<Point, Int>()
    private val map = matrixFromStringIndexed(input, ' ') { char, y, x ->
        when (char) {
            'v' -> {
                spaceShip.add(SpaceShip(Point(x, y), Direction.DOWN))
                '|'
            }

            '^' -> {
                spaceShip.add(SpaceShip(Point(x, y), Direction.UP))
                '|'
            }

            '>' -> {
                spaceShip.add(SpaceShip(Point(x, y), Direction.RIGHT))
                '-'
            }

            '<' -> {
                spaceShip.add(SpaceShip(Point(x, y), Direction.LEFT))
                '-'
            }

            '+' -> {
                intersections[Point(x, y)] = 0
                '+'
            }

            else -> char
        }
    }


    override fun solve1(): Long {
        var crash: Point? = null
        while ( crash == null) {
            spaceShip.sortBy { it.position }

            for (ship in spaceShip) {
                if (ship.isDead)
                    continue

                ship.step(map)
                spaceShip.firstOrNull { it != ship && !it.isDead && it.position == ship.position }.let {
                    if (it == null) return@let

                    crash = ship.position
                    ship.isDead = true
                    it.isDead = true
                }
            }
        }

        println("${crash!!.x},${crash!!.y}")
        return 0L
    }

    override fun solve2(): Long {
        while ( spaceShip.count { !it.isDead } != 1) {
            spaceShip.sortBy { it.position }

            for (ship in spaceShip) {
                if (ship.isDead)
                    continue

                ship.step(map)
                spaceShip.firstOrNull { it != ship && !it.isDead && it.position == ship.position }.let {
                    if (it == null) return@let

                    ship.isDead = true
                    it.isDead = true
                }
            }
        }
        spaceShip.first { !it.isDead }.position.let {
            println("${it.x},${it.y}")
        }

        return -1
    }
}

fun main() {
    val day = Day13(readFullText("_2018/d13/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day13(readFullText("_2018/d13/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}