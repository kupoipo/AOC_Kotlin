package _2020.d14

import util.Day
import util.allLong
import util.readFullText
import kotlin.system.measureNanoTime

class Day14(override val input: String) : Day<Long>(input) {
    private val map = mutableMapOf<Long, Long>()
    private var mask = ""

    fun or(numBinary: String, mask: String): Long {
        return buildString {
            mask.indices.forEach {
                if (mask[it] == 'X') append(numBinary[it])
                else append(mask[it])
            }
        }.toLong(2)
    }


    override fun solve1(): Long {
        for (line in input.split("\n")) {
            if (line.contains("mask")) {
                mask = line.substring(line.lastIndexOf(" ") + 1)
            } else {
                val numbers = line.allLong()
                map[numbers.first()] = or(numbers.last().toString(2).padStart(mask.length, '0'), mask)
            }
        }

        return map.values.sum()
    }

    private fun getRecursivePossibility(newString: String): Set<Long> {
        if (!newString.contains("X")) return mutableSetOf(newString.toLong(2))

        val res = mutableSetOf<Long>()

        res.addAll(getRecursivePossibility(newString.replaceFirst('X', '0')))
        res.addAll(getRecursivePossibility(newString.replaceFirst('X', '1')))

        return res
    }

    private fun orPart2(numBinary: String, mask: String): MutableSet<Long> {
        val newString = buildString {
            mask.indices.forEach {
                when (mask[it]) {
                    'X' -> append('X')
                    '0' -> append(numBinary[it])
                    else -> append('1')
                }
            }
        }

        return getRecursivePossibility(newString).toMutableSet()
    }


    override fun solve2(): Long {
        map.clear()
        for (line in input.split("\n")) {
            if (line.contains("mask")) {
                mask = line.substring(line.lastIndexOf(" ") + 1)
            } else {
                val numbers = line.allLong()
                orPart2(numbers.first().toString(2).padStart(mask.length, '0'), mask).forEach {
                    map[it] = numbers.last()
                }
            }
        }

        return map.values.sum()
    }
}

fun main() {
    val day = Day14(readFullText("_2020/d14/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day14(readFullText("_2020/d14/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}