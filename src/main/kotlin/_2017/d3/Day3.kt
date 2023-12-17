package _2017.d3

import util.*
import kotlin.system.measureNanoTime

class Day3(override val input: String) : Day<Long>(input) {
    val goal = 265149
    override fun solve1(): Long {
        var x = 0
        var y = 0
        var size = 1
        var num = 1
        while (num != goal) {
            repeat(size) {
                num++
                x++
                if (num == goal)
                    return Point(0, 0).manhattan(Point(x, y)).toLong()
            }

            repeat(size) {
                num++
                y--
                if (num == goal)
                    return Point(0, 0).manhattan(Point(x, y)).toLong()
            }

            size++

            repeat(size) {
                num++
                x--
                if (num == goal)
                    return Point(0, 0).manhattan(Point(x, y)).toLong()
            }
            repeat(size) {
                num++
                y++
                if (num == goal)
                    return Point(0, 0).manhattan(Point(x, y)).toLong()
            }

            size++
        }

        return 0
    }
    val map : Matrix<Int> = emptyMatrixOf(30,30, -1)

    fun calc(x: Int, y: Int) {
        var num : Int
        Point(x, y).let {
            num = it.adjacent(true).sumOf { if (map[it] != -1) map[it] else 0 }
            map[it] = num
        }
    }

    override fun solve2(): Long {


        var x = map.size/2
        var y = map[0].size/2
        map[y][x] = 1
        var size = 1
        var num = 1
        while (true) {
            repeat(size) {
                x++
                calc(x, y)
                if (map[y][x] > goal)
                    return map[y][x].toLong()

            }
            repeat(size) {
                y--
                calc(x, y)
                if (map[y][x] > goal)
                    return map[y][x].toLong()
            }

            size++

            repeat(size) {
                x--
                calc(x, y)
                if (map[y][x] > goal)
                    return map[y][x].toLong()
            }
            repeat(size) {
                y++
                calc(x, y)
                if (map[y][x] > goal)
                    return map[y][x].toLong()
            }

            size++
        }
    }
}

fun main() {
    val day = Day3(readFullText("_2017/d3/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day3(readFullText("_2017/d3/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}