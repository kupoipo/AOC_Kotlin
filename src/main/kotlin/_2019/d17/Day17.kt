package _2019.d17

import _2019.IntCode
import util.*
import kotlin.system.measureNanoTime

val test = "..#..........\n" +
        "..#..........\n" +
        "#######...###\n" +
        "#.#...#...#.#\n" +
        "#############\n" +
        "..#...#...#..\n" +
        "..#####...^.."

class Day17(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    var intCode = if (isTest) null else IntCode(input)
    var map: Matrix<Char> = matrixFromString(
        if (isTest) test else intCode!!.execute().let { intCode!!.output.map { it.toInt().toChar() } }.joinToString(""),
        '.'
    ) { it }

    private val intersections = map.points().filter { p ->
        Direction.values().all { (p + it).let { it.inMap(map) && map[it] == '#' } }
    }

    override fun solve1(): Long = intersections.map { it.y * it.x }.sum()

    override fun solve2(): Long {
        intCode = IntCode("2${input.drop(1)}",  freeInputMode = true )
        intCode!!.execute(true) {
            it.toInt().toChar().toString()
        }
        return -1
    }
}

fun main() {
    val day = Day17(false, readFullText("_2019/d17/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day17(true, readFullText("_2019/d17/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}