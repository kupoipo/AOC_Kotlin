package _2018.d10

import util.*
import kotlin.system.measureNanoTime

class Bird(val position: Point, val velocity: Point) {
    fun fly() {
        position.x = position.x + velocity.x
        position.y = position.y + velocity.y
    }
}

class Day10(override val input: String) : Day<Long>(input) {
    private val birds =
        input.split("\n").map { line -> line.allInts().let { Bird(Point(it[0], it[1]), Point(it[2], it[3])) } }
    private val map: Matrix<Char>

    init {
        var ecart = 300
        var seconds = 0
        var topLeft = Point(0, 0)
        var bottomRight = Point(0, 0)
        while (ecart != 9) {
            birds.forEach { it.fly() }
            seconds++
            topLeft = Point(birds.minOf { it.position.x }, birds.minOf { it.position.y })
            bottomRight = Point(birds.maxOf { it.position.x }, birds.maxOf { it.position.y })

            ecart = (bottomRight.y - topLeft.y).toInt()
        }
        birds.forEach {
            it.position.x = it.position.x - topLeft.x
            it.position.y = it.position.y - topLeft.y
        }

        map = emptyMatrixOf(10, (bottomRight.x - topLeft.x + 1).toInt(), '.')
        display()
        println("Time to wait : $seconds")
    }

    fun display() {
        map.forEachPoint { map[it] = '.' }
        birds.forEach {
            map[it.position] = '#'
        }
        showMap(map)
    }

    fun run() {
        birds.forEach { it.fly() }
    }

    override fun solve1(): Long {
        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day10(readFullText("_2018/d10/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day10(readFullText("_2018/d10/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}