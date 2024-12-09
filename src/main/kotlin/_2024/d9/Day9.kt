package _2024.d9

import util.Day
import util.readFullText
import kotlin.math.max
import kotlin.system.measureNanoTime

class Day9(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class MemoryInformation(val size: Int, val index: Int, val value: Long)

    private val memory = MutableList(input.sumOf { it.digitToInt() }) { -1L }

    private fun initMemory() {
        var numberID = 0L
        var indexMemory = 0

        input.windowed(2, 2, true).forEach {
            val fill = it.first().digitToInt()
            val empty = it.last().digitToInt()
            repeat(fill) {
                memory[indexMemory] = numberID
                indexMemory++
            }
            repeat(empty) {
                if (indexMemory > memory.lastIndex) return@repeat
                memory[indexMemory] = -1
                indexMemory++
            }
            if (fill != 0) numberID++
        }
    }


    override fun solve1(): Long {
        var startIndex = 0
        var endIndex = memory.lastIndex

        initMemory()

        while (startIndex < endIndex) {
            while (memory[endIndex] == -1L) endIndex--
            while (memory[endIndex] != -1L && startIndex < endIndex) {
                while (memory[startIndex] != -1L) startIndex++
                memory[startIndex] = memory[endIndex]
                memory[endIndex] = -1
                endIndex--
                startIndex++
            }
        }
        return memory.takeWhile { it != -1L }.reduceIndexed { index, acc, value -> acc + value * index }
    }

    override fun solve2(): Long {
        val memoryInformation = mutableListOf<MemoryInformation>()
        val emptySpaces = mutableListOf<Pair<Int, Int>>()
        initMemory()

        var current = -1L
        var count = 0
        for ((index, value) in memory.withIndex()) {
            if (current != value) {
                if (current != -1L) {
                    memoryInformation.add(MemoryInformation(count, index - count, current))
                } else {
                    emptySpaces.add(count to index - count)
                }
                current = value
                count = 1
            } else {
                count++
            }
        }
        memoryInformation.add(MemoryInformation(count, memory.size - count, current))
        memoryInformation.sortByDescending { it.value }

        for (information in memoryInformation) {
            val indexFree =
                emptySpaces.filter { it.first >= information.size && it.second < information.index }.minByOrNull { it.second }

            if (indexFree != null) {
                repeat(information.size) {
                    memory[indexFree.second + it] = information.value
                    memory[information.index + it] = -1
                }

                emptySpaces.remove(indexFree)

                /**
                 * Maybe we let a smaller free space instead of just fulling it, so we have to update information.
                 */
                if (indexFree.first != information.size) {
                    emptySpaces.add(indexFree.first - information.size to indexFree.second + information.size)
                }
            }
        }

        return memory.reduceIndexed { index, acc, value -> if (value == -1L) acc else acc + value * index }
    }
}


fun main() {
    val day = Day9(false, readFullText("_2024/d9/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day9(true, readFullText("_2024/d9/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}