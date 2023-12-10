package _2023.d10

import _2022.d22.direction
import util.*
import java.lang.Exception
import kotlin.system.measureNanoTime

class Day10(override val input: String) : Day<Long>(input) {
    var startingPoint: Point

    lateinit var mapLoop: Matrix<Boolean>
    var map: Matrix<Pipe> = matrixFromString(input, Pipe.NONE) {
        Pipe.pipeFrom(it)
    }

    init {
        startingPoint = map.pointOfFirst { it == Pipe.START }
    }

    fun getLoop(p: Pipe): List<Point> {
        var position = startingPoint
        var direction = p.startingDirection
        var currentPipe: Pipe
        val res = mutableListOf(position)

        map[position] = p
        mapLoop = matrixFromString(input, false) { false }

        do {
            mapLoop[position] = true
            map[position].nextPosition(direction, position).let {
                position = it.second
                direction = it.first
            }

            if (position.outOfMap(map)) {
                return listOf()
            }

            currentPipe = map[position]
            if (currentPipe == Pipe.NONE) {
                return listOf()
            }
            if (!currentPipe.accept(direction)) {
                return listOf()
            }

            res.add(position)
        } while ((position != startingPoint))

        return res
    }

    override fun solve1(): Long {
        for (p in Pipe.values()) {
            if (p != Pipe.NONE && p != Pipe.START) {
                getLoop(p).let {
                    if (it.isNotEmpty()) {
                        return (it.size / 2).toLong()
                    }
                }
            }
        }
        return -1
    }

    fun isInsideLoop(p: Point): Boolean {
        if (mapLoop[p]) return false

        var count = 0
        var currentPosition = p
        while (!currentPosition.outOfMap(map)) {
            if (mapLoop[currentPosition]) {
                if (map[currentPosition] == Pipe.VERTICAL || map[currentPosition] == Pipe.SOUTH_EAST || map[currentPosition] == Pipe.SOUTH_WEST)
                    count++
            }

            currentPosition += Direction.LEFT
        }
        
        return count % 2 != 0
    }

    override fun solve2(): Long = map.points().count { isInsideLoop(it) }.toLong()
}


fun main() {
    val day = Day10(readFullText("_2023/d10/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day10(readFullText("_2023/d10/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}