package _2016.d8

import util.*
import kotlin.system.measureNanoTime

class Day8(override val input: String) : Day<Long>(input) {
    val points = mutableSetOf<Point>()

    private fun createRect(x: Int, y: Int) {
        repeat(y) { l ->
            repeat(x) { c ->
                points.add(Point(c, l))
            }
        }
    }



    private fun rotateCol(x: Int, offSet: Int) {
        val x = x.toLong()
        val pToMove = points.filter { x == it.x }
        points.removeIf { it.x == x }

        pToMove.forEach {
            points.add(Point(it.x, (it.y + offSet) % LINES))
        }
    }

    private fun rotateLine(y: Int, offSet: Int) {
        val y = y.toLong()
        val pToMove = points.filter { y == it.y }
        points.removeIf { it.y == y }

        pToMove.forEach {
            points.add(Point((it.x + offSet) % COLUMNS, it.y))
        }
    }

    fun display() {
        val m = emptyMatrixOf(LINES, COLUMNS, '.')

        points.forEach {
            m[it] = '#'
        }
        showMap(m)
    }

    init {
        input.split("\n").forEach {
            val i = it.allInts()

            if (it.contains("rect")) {
                createRect(i[0], i[1])
            } else if (it.contains("column")) {
                rotateCol(i[0], i[1])
            } else {
                rotateLine(i[0], i[1])
            }

        }
    }

    override fun solve1(): Long = points.size.toLong()
    override fun solve2(): Long {
        display()
        return -1
    }

    companion object {
        const val COLUMNS = 50
        const val LINES = 6
    }
}

fun main() {
    val day = Day8(readFullText("_2016/d8/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day8(readFullText("_2016/d8/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}