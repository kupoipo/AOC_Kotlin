
package _2021.d25

import util.*
import kotlin.system.measureNanoTime
class Day25(private val isTest: Boolean, override val input : String) : Day<Long>(input) {
    var map = matrixFromString(input, '.') { it }

    fun move() : Boolean {
        val nextMap = emptyMatrixOf(map.size, map.nbColumns, '.')
        map.forEachPoint { nextMap[it] = map[it] }

        map.points().filter { map[it] == '>' }.forEach {
            if (map[it.y][(it.x + 1)%map.nbColumns] == '.') {
                nextMap[it] = '.'
                nextMap[it.y][(it.x + 1)%map.nbColumns] = '>'
            }
        }
        val tempMap = emptyMatrixOf(nextMap.size, nextMap.nbColumns, '.')
        nextMap.forEachPoint { tempMap[it] = nextMap[it] }

        tempMap.points().filter { tempMap[it] == 'v' }.forEach {
            if (tempMap[(it.y + 1)%tempMap.size][it.x ] == '.') {
                nextMap[it] = '.'
                nextMap[(it.y + 1)%tempMap.size][it.x] = 'v'
            }
        }

        val res = nextMap != map
        map = nextMap
        return res
    }

    override fun solve1(): Long {
        var cpt = 0L
        while( move() ) {
            cpt++
        }
        return cpt
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day25(false, readFullText("_2021/d25/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day25(true, readFullText("_2021/d25/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}