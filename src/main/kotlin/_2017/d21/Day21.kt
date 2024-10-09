package _2017.d21

import util.*
import kotlin.system.measureNanoTime

class Day21(override val input: String) : Day<Long>(input) {
    var map: Matrix<Char> = mutableListOf(".#.".toMutableList(), "..#".toMutableList(), "###".toMutableList())
    private val rules = input.split("\n").map {
        it.split(" => ")
            .let { (a, b) ->
                matrixFromString(
                    a.findAllMatch("""(#|\.)+(?=(/|$))""").joinToString("\n"),
                    '.'
                ) { it } to matrixFromString(
                    b.findAllMatch("""(#|\.)+(?=(/|$))""").joinToString("\n"), '.'
                ) { it }
            }
    }

    private fun enhancementRule(tile: Matrix<Char>): Matrix<Char> {
        for (r in rules) {
            repeat(2) {
                repeat(4) {
                    tile.rotateRight()

                    if (tile == r.first) return r.second.clone()
                }
                tile.flipX()
            }
        }

        throw Exception("No rule found for $tile.")
    }

    private fun nextState() {
        val size = if (map.size % 2 == 0) 2 else 3

        val mapTemp = map.windowed(size, size).map { pairs ->
            buildList {
                for (j in pairs[0].indices step size) {
                    add(buildList {
                        for (line in pairs) {
                            add(line.subList(j, j + (size)))
                        }
                    })
                }
            }
        }.flatten()

        val nbFusion = if (map.size % 2 == 0) map.size / 2 else map.size / 3
        val res = emptyMatrixOf(0, 0, '.')

        mapTemp.map { enhancementRule(it.toMutableList()) }.windowed(nbFusion, nbFusion).forEach {
            for (i in it[0].indices) {
                val line = mutableListOf<Char>()
                for (matrix in it) {
                    line.addAll(matrix[i])
                }
                res.add(line)
            }
        }

        map = res
    }

    override fun solve1(): Long {
        repeat(18) {
            nextState()
        }
        return map.sumOf { it.count { c -> c == '#' } }.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day21(readFullText("_2017/d21/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day21(readFullText("_2017/d21/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}