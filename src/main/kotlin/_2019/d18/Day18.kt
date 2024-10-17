package _2019.d18

import util.*
import kotlin.system.measureNanoTime

class Day18(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    override fun solve1(): Long {
        val level = Level.levelFrom(input)

        return State.shortestPastFrom(level.state)!!.time.toLong()
    }

    override fun solve2(): Long {
        val map = matrixFromString(input, '.') { it }
        val p = map.points().first { map[it] == '@' }

        Point(0, 0).forEachEveryNeighbors(true) {
            if (it.manhattan(Point(0, 0)) == 2L) map[it + p] = '@'
            else map[it + p] = '#'
        }

        val upLeft = map.subMap(0, 0, map.nbColumns / 2 + 1, map.size / 2 + 1)
        val upRight = map.subMap(map.nbColumns / 2, 0, map.nbColumns / 2 + 1, map.size / 2 + 1)
        val bottomLeft = map.subMap(0, map.size / 2, map.nbColumns / 2 + 1, map.size / 2 + 1)
        val bottomRight = map.subMap(map.nbColumns / 2, map.size / 2, map.nbColumns / 2 + 1, map.size / 2 + 1)

        val subMaps = listOf(upLeft, upRight, bottomRight, bottomLeft)
        val players = subMaps.map { subMap -> subMap.pointOfFirst { it == '@' } }

        val state = StateD182019Part2(
            subMaps.map { Level.levelFrom(it.toFlatString()) },
            players,
            input.filter { it in 'a'..'z' }.toSet(),
            emptySet(),
            0,
            null
        )

        return State.shortestPastFrom(state)!!.time.toLong()
    }
}

fun main() {
    val day = Day18(false, readFullText("_2019/d18/input"))
    // println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day18(true, readFullText("_2019/d18/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}