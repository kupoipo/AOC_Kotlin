package _2023.d12

import util.*
import kotlin.math.pow
import kotlin.system.measureNanoTime

class WeatherLine(val line: String, val snowGroups: MutableList<Int>) {
    val snowPositions = mutableListOf<Int>()
    val unknownPositions = mutableListOf<Int>()
    val proxy = hashMapOf<Pair<List<Char>, List<Int>>, Long>()

    init {
        snowPositions.addAll(line.allIndexOf("#"))
        unknownPositions.addAll(line.allIndexOf("?"))
    }

    private fun nbArrangementRecu(currentLine: List<Char>, currentSnowGroup: List<Int>): Long {
        /**
         * Every snow block have been placed, we verify that there are none left, otherwise we exceed the quota of the line
         */
        if (currentSnowGroup.isEmpty()) return if (currentLine.contains(SNOW)) 0 else 1

        /**
         * Not enough space to place every snow block
         */
        if (currentLine.size < currentSnowGroup.sum()) return 0

        return proxy.getOrPut(currentLine to currentSnowGroup) {
            var total = 0L

            /**
             * We treat the UNKNOWN as SNOW, we try to remove the first snow group if possible
             * It's possible if :
             *      - There is no NOTHING in the ``currentSnowGroup.first()`` first's char of ``currentLine``
             *      - We must verify that the element at ``currentSnowGroup.first()`` in currentLine isn't a snow or we would have a
             *      ``currentSnowGroup.first()`` + X group instead of a ``currentSnowGroup.first()``
             */
            if (currentLine.first() in listOf(UNKNOWN, SNOW )
                && !currentLine.take(currentSnowGroup.first()).contains(NOTHING)
                && (currentSnowGroup.first() == currentLine.size || currentLine[currentSnowGroup.first()] != SNOW)
            ) {
                total += nbArrangementRecu(
                    currentLine.drop(currentSnowGroup.first() + 1),
                    currentSnowGroup.drop(1)
                )
            }

            /**
             * We treat the UNKNOWN as NOTHING so we just go forward
             */
            if (currentLine.first() in listOf(UNKNOWN, NOTHING)) {
                total += nbArrangementRecu(currentLine.drop(1), currentSnowGroup)
            }

            total

        }
    }

    fun nbArrangements(): Long {
        return nbArrangementRecu(line.toList(), snowGroups.toList())
    }

    companion object {
        const val SNOW = '#'
        const val UNKNOWN = '?'
        const val NOTHING = '.'
    }
}

class Day12(override val input: String) : Day<Long>(input) {
    val lines = input.split("\n").map { line ->
        val data = line.split(" ")
        WeatherLine(data[0], data[1].allInts())
    }

    init {
        println(lines.mapIndexed { index, it ->
            index to WeatherLine((("?" + it.line) * 5).drop(1), it.snowGroups * 5).nbArrangements()
        }.sortedBy { it.first })
    }

    override fun solve1(): Long = lines.sumOf { it.nbArrangements() }

    override fun solve2(): Long =
        lines.sumOf {
            WeatherLine((("?" + it.line) * 5).drop(1), it.snowGroups * 5).nbArrangements()
        }

}


fun main() {
    val day = Day12(readFullText("_2023/d12/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day12(readFullText("_2023/d12/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}