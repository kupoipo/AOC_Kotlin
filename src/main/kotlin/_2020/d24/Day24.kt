package _2020.d24

import _2022.d22.position
import util.Day
import util.Point3DLong
import util.readFullText
import kotlin.system.measureNanoTime

class Day24(override val input: String) : Day<Long>(input) {
    class Hex(val pos: Point3DLong) {
        var isBlack = false
        fun getNeighbor(i: Int): Hex {
            val newPoint = when (i) {
                0 -> pos + Point3DLong(0, -1, 1)
                1 -> pos + Point3DLong(1, -1, 0)
                2 -> pos + Point3DLong(1, 0, -1)
                3 -> pos + Point3DLong(0, 1, -1)
                4 -> pos + Point3DLong(-1, 1, 0)
                else -> pos + Point3DLong(-1, 0, 1)
            }

            map.putIfAbsent(newPoint, Hex(newPoint))

            return map[newPoint]!!
        }

        override fun toString(): String {
            return "$pos - is black : $isBlack";
        }

        companion object {
            var nbBlack = 0L
            var map = mutableMapOf<Point3DLong, Hex>().also { it[Point3DLong(0, 0, 0)] = Hex(Point3DLong(0,0,0)) }
        }
    }

    private fun changeHex(path: String) {
        var dir = ""
        var current = Hex.map[Point3DLong(0,0,0)]!!

        for (c in path) {
            current = when (c) {
                'e' -> {
                    if (dir.isEmpty()) current.getNeighbor(0)
                    else {
                        if (dir == "n") current.getNeighbor(5)
                        else current.getNeighbor(1)
                    }.also { dir = "" }
                }

                'w' -> {
                    if (dir.isEmpty()) current.getNeighbor(3)
                    else {
                        if (dir == "n") current.getNeighbor(4)
                        else current.getNeighbor(2)

                    }.also { dir = "" }
                }

                else -> {
                    dir += c
                    current
                }
            }
        }


        current.isBlack = !current.isBlack
    }

    fun day() {
        val toFlip = mutableSetOf<Point3DLong>()
        val coordinates = Hex.map.keys.toMutableSet()

        Hex.map.keys.forEach { pos ->
            coordinates.add(pos + Point3DLong(0, -1, 1))
            coordinates.add(pos + Point3DLong(1, -1, 0))
            coordinates.add(pos + Point3DLong(1, 0, -1))
            coordinates.add(pos + Point3DLong(0, 1, -1))
            coordinates.add(pos + Point3DLong(-1, 1, 0))
            coordinates.add(pos + Point3DLong(-1, 0, 1))
        }

        val neighbors = mutableListOf<Point3DLong>()
        neighbors.add(Point3DLong(0, -1, 1))
        neighbors.add(Point3DLong(1, -1, 0))
        neighbors.add(Point3DLong(1, 0, -1))
        neighbors.add(Point3DLong(0, 1, -1))
        neighbors.add(Point3DLong(-1, 1, 0))
        neighbors.add(Point3DLong(-1, 0, 1))

        coordinates.forEach { p ->
            val nbNeighborsBlack = neighbors.count { Hex.map[it + p] != null && Hex.map[it + p]!!.isBlack }

            if (Hex.map.getOrPut(p) { Hex(p) }.isBlack) {
                if (nbNeighborsBlack == 0 || nbNeighborsBlack > 2) toFlip.add(p)
            } else {
                if (nbNeighborsBlack == 2) toFlip.add(p)
            }
        }

        toFlip.forEach { Hex.map[it]!!.let { it.isBlack = !it.isBlack } }
    }

    override fun solve1(): Long {
        Hex.map = mutableMapOf<Point3DLong, Hex>().also { it[Point3DLong(0, 0, 0)] = Hex(Point3DLong(0,0,0)) }
        input.split("\n").forEach { changeHex(it) }
        return Hex.map.values.count { it.isBlack }.toLong()
    }

    override fun solve2(): Long {
        repeat(100) {
            day()
        }
        return Hex.map.values.count { it.isBlack }.toLong()
    }
}

fun main() {
    val day = Day24(readFullText("_2020/d24/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day24(readFullText("_2020/d24/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}