package _2025.d4

import util.*
import kotlin.system.measureNanoTime

class Day4(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val map = matrixFromString(input, '.') { it }

    private fun nbNeighbors(p: Point) = p.adjacent().count {
        it.inMap(map) && map[it] == '@'
    }

    private fun papersToRemove() = map.points().filter { map[it] == '@' && nbNeighbors(it) < 4 }

    override fun solve1(): Long = papersToRemove().size.toLong()

    override fun solve2(): Long {
        var cpt = 0L

        do {
            val toRemove = papersToRemove()
            toRemove.forEach { map[it] = '.' }
            cpt += toRemove.size
        } while (toRemove.isNotEmpty())

        return cpt
    }
}

fun main() {
    val day = Day4(false, readFullText("_2025/d4/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day4(true, readFullText("_2025/d4/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}