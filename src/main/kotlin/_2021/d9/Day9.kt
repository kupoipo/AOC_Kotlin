package _2021.d9

import d9.affiche
import util.*

import kotlin.system.measureTimeMillis
class Day9(override val input : String) : Day<Int>(input) {
    val map : Matrix<Int> = matrixFromString(input, 0, ::charToInt)
    val lowPoint = mutableSetOf<Point>()
    override fun solve1(): Int {
        map.forEachPoint { p ->
            var isLow = true
            p.forEachNeighbors {
                if (it.inMap(map)) {
                    if (map[it] <= map[p] )
                        isLow = false
                }
            }

            if (isLow) lowPoint.add(p)
        }

        return lowPoint.map { map[it] + 1 }.sum()
    }

    var visited : MutableSet<Point> = mutableSetOf()

    fun nbBassin(p : Point) : Int {
        var nbNeighbors = 1

        visited.add(p)

        p.forEachNeighbors {
            if (it.inMap(map) && map[it] < 9 && map[it] >=  + 1 && it !in visited) {

                nbNeighbors += nbBassin(it)
            }
        }

        return nbNeighbors
    }

    override fun solve2(): Int {
        visited = mutableSetOf()

        var i = lowPoint.map { nbBassin(it) }.sortedDescending().take(3).reduce(Int::times)

        return i
    }
}




fun main() {
    var dayTest = Day9(readFullText("_2021/d9/test"))
    var day = Day9(readFullText("_2021/d9/input"))

    dayTest.solve1()
    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}