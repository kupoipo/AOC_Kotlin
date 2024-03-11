package _2018.d3

import util.*
import java.lang.Exception
import kotlin.system.measureNanoTime

class Day3(override val input: String) : Day<Long>(input) {
    class Square(val x: Long, val y: Long, val width: Long, val height: Long) {
        fun claims(other: Square): Set<Point> {
            return buildSet {
                try {
                    for (y in (y..(y + height)).overlap(other.y..other.y + other.height)) {
                        for (x in (x..(x + width)).overlap(other.x..other.x + other.width)) {
                            add(Point(y, x))
                        }
                    }
                } catch (_: Exception) {

                }
            }
        }
    }

    private val squares = input.split("\n").map { line -> line.allLong().let { Square(it[1], it[2], it[3] - 1, it[4] - 1) } }
    private val isClaiming = MutableList(squares.size) { false }

    override fun solve1(): Long {
        return buildSet {
            for (i in squares.indices) {
                for (j in i+1..squares.lastIndex) {
                    val combination = (squares[i] to squares[j])
                    val claim = combination.first.claims(combination.second)
                    addAll(claim)

                    if (claim.isNotEmpty()) {
                        isClaiming[i] = true
                        isClaiming[j] = true
                    }
                }
            }
        }.size.toLong()
    }

    override fun solve2(): Long {
        return (isClaiming.indexOfFirst { !it } + 1).toLong()
    }
}

fun main() {
    val day = Day3(readFullText("_2018/d3/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day3(readFullText("_2018/d3/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}