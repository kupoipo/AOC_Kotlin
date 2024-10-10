package _2020.d21

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day21(override val input: String) : Day<Long>(input) {
    private val allergens = mutableMapOf<String, MutableSet<String>>()
    private val translation = mutableListOf<Pair<String, String>>()
    val data = input.split("\n").map {
        it.dropLast(1).split(" (").let { line ->
            line.first().split(" ") to line.last().dropWhile { c -> c != ' ' }.trim().split(",").map { it.trim() }
        }
    }

    init {
        data.forEach { (foreign, english) ->
            english.forEach { word ->
                allergens.putIfAbsent(word, mutableSetOf())
            }
        }

        data.forEach { (foreign, english) ->
            english.forEach { word ->
                if (allergens[word]!!.isEmpty()) {
                    allergens[word]!!.addAll(foreign)
                } else {
                    allergens[word] = allergens[word]!!.intersect(foreign.toSet()) as MutableSet<String>
                }
            }
        }
    }

    override fun solve1(): Long {
        return data.sumOf { it.first.size - it.first.count { word -> allergens.values.any { it.contains(word) } } }.toLong()
    }

    override fun solve2(): Long {
        while (allergens.values.any { it.size == 1 }) {
            val find = allergens.filter { it.value.size == 1 }

            find.forEach { solution ->
                translation.add(solution.key to solution.value.first())

                allergens.forEach {
                    if (it.key != solution.key) it.value.remove(solution.value.first())
                }

                allergens.remove(solution.key)
            }
        }

        translation.sortBy { it.first }
        println(translation.joinToString { it.second }.replace(" ", ""))

        return -1
    }
}

fun main() {
    val day = Day21(readFullText("_2020/d21/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day21(readFullText("_2020/d21/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}