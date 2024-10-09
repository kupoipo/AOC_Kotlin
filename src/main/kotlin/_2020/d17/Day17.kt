package _2020.d17

import util.*
import kotlin.system.measureNanoTime

class Day17(override val input: String) : Day<Long>(input) {
    var points = buildSet {
        val map: Matrix<Char> = matrixFromString(input, '.') { it }
        map.forEachPoint {
            if (map[it] == '#') {
                this.add(Point4DLong(it.x, it.y, 0, 0))
            }
        }
    }

   /* fun step3D() {
        val newPoints = mutableSetOf<Point3DLong>()
        val zRange = (points.minOf { it.z } - 1)..(points.maxOf { it.z } + 1)
        val xRange = (points.minOf { it.x } - 1)..(points.maxOf { it.x } + 1)
        val yRange = (points.minOf { it.y } - 1)..(points.maxOf { it.y } + 1)

        for (z in zRange) {
            for (y in yRange) {
                for (x in xRange) {
                    val p = Point3DLong(x, y, z)
                    val neighbors = p.all26Neighbors().count { points.contains(it) }

                    if (neighbors == 3 && p !in points) {
                        newPoints.add(p)
                    }

                    if (neighbors in 2..3 && p in points) {
                        newPoints.add(p)
                    }
                }
            }
        }

        /*for (y in yRange) {
            for (x in xRange) {
                if (Point3DLong(x, y, 0) in points) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }*/
        println(points.size.toLong())

        points = newPoints
    }*/

    fun step4D() {
        val newPoints = mutableSetOf<Point4DLong>()
        val zRange = (points.minOf { it.z } - 1)..(points.maxOf { it.z } + 1)
        val xRange = (points.minOf { it.x } - 1)..(points.maxOf { it.x } + 1)
        val yRange = (points.minOf { it.y } - 1)..(points.maxOf { it.y } + 1)
        val wRange = (points.minOf { it.w } - 1)..(points.maxOf { it.w } + 1)

        for (w in wRange)
            for (z in zRange) {
                for (y in yRange) {
                    for (x in xRange) {
                        val p = Point4DLong(x, y, z, w)
                        val neighbors = p.allNeighbors().count { points.contains(it) }

                        if (neighbors == 3 && p !in points) {
                            newPoints.add(p)
                        }

                        if (neighbors in 2..3 && p in points) {
                            newPoints.add(p)
                        }
                    }
                }
            }

        points = newPoints
    }

    override fun solve1(): Long {
        return -1
    }

    override fun solve2(): Long {
        repeat(6) { step4D() }
        return points.size.toLong()
    }
}

fun main() {
    val day = Day17(readFullText("_2020/d17/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day17(readFullText("_2020/d17/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}