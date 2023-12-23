package _2017.d21

import util.*
import kotlin.system.measureNanoTime

class Day21(override val input: String) : Day<Long>(input) {
    var map : Matrix<Char> = mutableListOf(".#.".toMutableList(), "..#".toMutableList(), "###".toMutableList())

    //var map: Matrix<Char> = mutableListOf("#..#".toMutableList(), "....".toMutableList(), "....".toMutableList(), "#..#".toMutableList())

    var rules = input.split("\n").map {
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

    fun enhancementRule(tile: Matrix<Char>): Matrix<Char> {
        for (r in rules) {
            repeat(2) {
                repeat(4) {
                    tile.rotateRight()

                    if (tile == r.first) return r.second.clone()
                }
                tile.flip()
            }
        }

        throw Exception("No rule found for $tile.")
    }

    fun nextState() {
        val size = if (map.size % 3 == 0) 3 else 2

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

        println(mapTemp.map { enhancementRule(it.toMutableList()) })

        map = mapTemp.map { enhancementRule(it.toMutableList()) }.flatten().toMutableList()
    }

    override fun solve1(): Long {
        repeat(5) {
            nextState()
            println(map)
            println( map.sumOf { it.count { c -> c == '#' } }.toLong())
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