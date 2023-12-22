package _2023.d22

import util.Day
import util.Point3D
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

var map3D = MutableList(500) { MutableList(500) { MutableList(500) { -1 } } }

class Brick(var points: List<Point3D>, val id: Int, val from: String) {
    fun carriedBy(): Set<Int> {
        return buildSet {
            points.filter { it.z > 0 }.forEach { p ->
                map3D[p.x][p.y][p.z - 1].let {
                    if (it != -1) this.add(it)
                }
            }
        }.filter { it != id }.toSet()
    }

    fun fall() {
        while (points.all { p -> map3D[p.x][p.y][p.z - 1].let { return@all p.z - 1 > 0 && (map3D[p.x][p.y][p.z] == it || it == -1) } }) {
            points = buildList {
                points.forEach {
                    map3D[it.x][it.y][it.z] = -1
                    map3D[it.x][it.y][it.z - 1] = id
                    this.add(it + Point3D(0, 0, -1))
                }
            }
        }
    }

    fun nbBrickFallingIfDestroyed(bricks: List<Brick>): Long {
        val fallingBricks = mutableListOf(this)
        var total = 0L
        var indexFallingBrick = 0

        while (indexFallingBrick < fallingBricks.size) {
            val brickDestroyed = fallingBricks[indexFallingBrick]
            indexFallingBrick++

            brickDestroyed.points.forEach {
                val index = map3D[it.x][it.y][it.z + 1]

                if (index != brickDestroyed.id && index != -1) {
                    val brick = bricks[index]
                    if (brick.carriedBy().none { indexBrickCarrying -> bricks[indexBrickCarrying] !in fallingBricks } && brick !in fallingBricks) {
                        fallingBricks.add(bricks[index])
                        total++
                    }
                }
            }
        }

        return total
    }

    override fun toString(): String {
        return from
    }
}

class Day22(override val input: String) : Day<Long>(input) {
    val notDestroyable = mutableSetOf<Int>()
    init {
        map3D = MutableList(500) { MutableList(500) { MutableList(500) { -1 } } }
    }

    val bricks = input.split("\n").mapIndexed { index, line ->
        val b = mutableListOf<Point3D>()
        line.allInts().let { axes ->
            for (x in axes[0]..axes[3]) {
                for (y in axes[1]..axes[4]) {
                    for (z in axes[2]..axes[5]) {
                        b.add(Point3D(x, y, z))
                        map3D[x][y][z] = index
                    }
                }
            }
        }
        Brick(b, index, line)
    }

    override fun solve1(): Long {
        bricks.sortedBy { it.points.minOf { it.z } }.forEach { it.fall() }

        for (b in bricks) {
            b.carriedBy().let {
                if (it.size == 1) {
                    notDestroyable.add(it.first())
                }
            }
        }
        return (bricks.size - notDestroyable.size).toLong()
    }

    override fun solve2(): Long {
        return notDestroyable.sumOf { bricks[it].nbBrickFallingIfDestroyed(bricks) }
    }
}

fun main() {
    val day = Day22(readFullText("_2023/d22/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day22(readFullText("_2023/d22/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}