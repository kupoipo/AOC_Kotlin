package _2018.d12

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day12(override val input: String) : Day<Long>(input) {
    private var situation: String
    private var rules = mutableListOf<String>()

    init {
        input.split("\n").let { line ->
            situation = ".." + line.first().split(" ").last().trim() + ".."

            line.drop(2).forEach { rule ->
                rule.split(" => ").let {
                    if (it.last() == "#") {
                        rules.add(it.first())
                    }
                }
            }
        }
    }

    fun step() {
        var nextSituation = ".."
        situation = "..$situation.."
        for (i in 2..situation.lastIndex - 2) {
            val neighbors = situation.substring(i -2..i+2)
            nextSituation += if (rules.contains(neighbors)) {
                "#"
            } else
                "."
        }
        situation = "$nextSituation.."
    }

    private fun score(gen: Int) : Long{
        var sum = 0L
        situation.forEachIndexed { index, c ->
            if (c == '#') {
                sum += (index - (gen + 1) * 2)
            }
        }
        return sum
    }

    override fun solve1(): Long {
        val step = 20

        repeat(step) {
            step()
        }

        return score(20)
    }

    override fun solve2(): Long {
        repeat(179) {
            step()
        }
        val before = score(199)
        step()
        val after = score(200)
        return after + 49_999_999_800 * (after - before)
    }
}

fun main() {
    val day = Day12(readFullText("_2018/d12/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day12(readFullText("_2018/d12/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}