package _2020.d7

import util.Day
import util.findAllMatch
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime

class Day7(override val input: String) : Day<Long>(input) {

    init {
        initBags(input)
    }

    data class Bag(val color: String) {
        val content = mutableListOf<Pair<Int, String>>()

        fun getSize(): Long = if (content.isEmpty()) 1L
        else content.sumOf { bag -> bag.first * bags.first { it.color == bag.second }.getSize() } + 1L
    }


    override fun solve1(): Long {
        val queue = bags.filter { bag -> bag.content.any { it.second == SEARCHED_BAG } }.toMutableList()
        val contains = queue.toMutableSet()

        while (queue.isNotEmpty()) {
            val bag = queue.removeFirst()
            val newQueue = bags.filter { it.content.any { it.second == bag.color } }
            newQueue.forEach {
                contains.add(it)
                queue.add(it)
            }
        }

        return contains.size.toLong()
    }

    override fun solve2(): Long {
        return bags.first { it.color == SEARCHED_BAG }.getSize() - 1
    }

    companion object {
        const val SEARCHED_BAG = "shiny gold"

        private lateinit var bags: List<Bag>

        fun initBags(input: String) {
            bags = input.split("\n").map { line ->
                val color = line.take(line.indexOf("bags")).trim()
                val content = line.findAllMatch("""\d+[^0-9]*bag""")
                Bag(color).apply {
                    content.forEach { match ->
                        this.content.add(
                            match.firstInt() to match.substring(
                                match.indexOf(" ") + 1 until match.lastIndexOf(
                                    "bag"
                                )
                            ).trim()
                        )
                    }
                }
            }
        }
    }
}

fun main() {
    val day = Day7(readFullText("_2020/d7/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day7(readFullText("_2020/d7/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}