package _2019.d7

import _2019.IntCode
import util.Day
import util.permutations
import util.readFullText
import kotlin.system.measureNanoTime

class Day7(private val isTest: Boolean, override val input: String) : Day<Long>(input) {

    override fun solve1(): Long = permutations(listOf(0, 1, 2, 3, 4)).maxOf { settings ->
        settings.fold(0L) { acc, value ->
            IntCode(input, value.toLong(), acc).executeUntilOutput()
        }
    }


    override fun solve2(): Long = permutations(listOf(9, 8, 7, 6, 5)).maxOf { settings ->
        val amp = MutableList(5) { IntCode(input, settings[it].toLong(), 0) }
        var current = 0
        var res = 0L
        while (!amp.last().halted) {
            res = amp[current].executeUntilOutput()
            if (amp[current].halted) {
                amp.removeAt(current)
                if (current == amp.size) return@maxOf res
            }
            amp[(current + 1) % amp.size].inputInt = res
            current = (current + 1) % amp.size
        }
        res
    }
}

fun main() {
    val day = Day7(false, readFullText("_2019/d7/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day7(true, readFullText("_2019/d7/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")

    println()

    val dayTest2 = Day7(true, readFullText("_2019/d7/test2"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest2.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest2.solve2()) } / 1e9}s")
}