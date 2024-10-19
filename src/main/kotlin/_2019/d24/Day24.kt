package _2019.d24

import util.*
import kotlin.math.pow
import kotlin.system.measureNanoTime

class Day24(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    val previous = mutableSetOf<Matrix<Char>>()
    var map = matrixFromString(input, '.') { it }

    fun next(map: Matrix<Char>): Matrix<Char> {
        val res = emptyMatrixOf(map.size, map.nbColumns, '.')

        map.forEachPoint {
            val c = it.adjacent(diagonals = false).count { it.inMap(map) && map[it] == '#' }

            if (map[it] == '#') {
                res[it] = if (c == 1) '#' else '.'
            } else {
                res[it] = if (c == 1 || c == 2) '#' else '.'
            }
        }

        return res
    }

    override fun solve1(): Long {
        while (map !in previous) {
            previous.add(map)

            map = next(map)
        }
        return map.points().sumOf {
            if (map[it] == '#') 2.0.pow((it.y * 5 + it.x).toInt()).toLong()
            else 0
        }
    }

    val middle = Point(2, 2)

    class RecursiveGrid(map: Matrix<Int>) {
        var levels = mutableListOf(map)

        fun nextGen() {
            levels.add(0, emptyMatrixOf(5, 5, 0))
            levels.add(emptyMatrixOf(5, 5, 0))

            val nextLevels = mutableListOf<Matrix<Int>>()

            for (i in levels.indices) {
                val level = levels[i]
                val nextLevel = emptyMatrixOf(5, 5, 0)

                repeat(5) { y ->
                    repeat(5) { x ->
                        val p = Point(x, y)

                        if (p != Point(2,2)) {
                            var c = p.adjacent(diagonals = false).count { it.inMap(level) && level[it] == 1 }
                            if (y == 0 && i > 0) c += levels[i - 1][1][2]
                            if (y == 4 && i > 0) c += levels[i - 1][3][2]
                            if (x == 0 && i > 0) c += levels[i - 1][2][1]
                            if (x == 4 && i > 0) c += levels[i - 1][2][3]

                            if (p == Point(2, 1) && i < levels.size - 1) c += levels[i + 1][0].sum()
                            if (p == Point(2, 3) && i < levels.size - 1) c += levels[i + 1][4].sum()
                            if (p == Point(1, 2) && i < levels.size - 1) c += levels[i + 1].sumOf { it.first() }
                            if (p == Point(3, 2) && i < levels.size - 1) c += levels[i + 1].sumOf { it.last() }

                            if (level[p] == 1) {
                                nextLevel[p] = if (c == 1) 1 else 0
                            } else {
                                nextLevel[p] = if (c == 1 || c == 2) 1 else 0
                            }
                        }
                    }
                }

                nextLevels.add(nextLevel)
            }

            levels = nextLevels
        }

    }

    override fun solve2(): Long {
        var map = matrixFromString(input, 0) { if (it == '#') 1 else 0 }
        map[middle] = 0

        val recu = RecursiveGrid(map)
        repeat(200) { recu.nextGen() }

        return recu.levels.sumOf { it.sumOf { it.sum() } }.toLong()
    }
}

fun main() {
    val day = Day24(false, readFullText("_2019/d24/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day24(true, readFullText("_2019/d24/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}