package _2020.d23

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day23(override val input: String) : Day<Long>(input) {
    private val cups = input.map { it.digitToInt() }.toMutableList()
    private var currentIndexCup = 0
    private var currentCup = cups[currentIndexCup]
    private var maxValue = cups.max()

    fun pickThree(): List<Int> {
        return buildList {
            for (i in 1..3) {
                add(cups[(currentIndexCup + i) % cups.size])
            }
        }
    }

    fun move() {
        val selected = pickThree()
        var destination = currentCup - 1

        if (destination  == 0) destination = maxValue

        while (selected.any { it == destination }) {
            destination--
            if (destination == 0) destination = maxValue
        }
        val indexDestination = cups.indexOf(destination) + 1

        selected.reversed().forEach {
            cups.add(indexDestination, it)
        }

        if (indexDestination <= currentIndexCup) {
            repeat(3) {
                cups.add(cups.removeFirst())
            }
        }

        repeat(3) {
            cups.removeAt(currentIndexCup + 1)
        }

        currentIndexCup = (currentIndexCup + 1) % cups.size
        currentCup = cups[currentIndexCup]
    }

    override fun solve1(): Long {
        repeat(100) {
            move()
        }
        val one = cups.indexOf(1)

        return cups.subList(one, cups.size).let {
            it.addAll(cups.subList(0, one))
            it
        }.joinToString("").drop(1).toLong()
    }

    override fun solve2(): Long {
        val cups = input.map { it.digitToInt() }.toMutableList()

        for (i in 10..1_000_000) {
            cups.add(i)
        }

        maxValue = 1_000_000
        repeat(100_000_000) {
            move()
        }

        val one = cups.indexOf(1)

        return (cups[one+1] * cups[one+2]).toLong()
    }
}

fun main() {
    val day = Day23(readFullText("_2020/d23/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day23(readFullText("_2020/d23/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}