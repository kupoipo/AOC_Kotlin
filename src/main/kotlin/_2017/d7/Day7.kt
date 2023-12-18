package _2017.d7

import util.Day
import util.findAllMatch
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime

class Circus(var parent: Circus?, var weight: Int) {
    val sons = mutableListOf<Circus>()
}

class Day7(override val input: String) : Day<String>(input) {
    private val circus = mutableMapOf<String, Circus>()

    init {
        input.split("\n").forEach { line ->
            val w = line.firstInt()

            line.findAllMatch("""[a-z]+""").let { list ->
                val c = circus.getOrPut(list.first()) { Circus(null, w) }
                c.weight = w

                list.drop(1).forEach {
                    val son = circus.getOrPut(it) { Circus(c, 0) }
                    c.sons.add(son)
                    son.parent = c
                }
            }
        }
    }

    override fun solve1(): String {
        return circus.keys.first { circus[it]!!.parent == null }
    }

    fun findProblem(key: Circus) : Int {
        if (key.sons.size == 0)
            return key.weight

        val size = findProblem(key.sons.first())
        var totalSize = size + key.weight

        key.sons.drop(1).forEach {
            val localSize = findProblem(it)

            if (localSize != size) {
                println("$localSize - $size")
                println(key.sons.first().weight)
                println(it.weight)
            }

            totalSize += localSize
        }

        return totalSize
    }

    override fun solve2(): String {
        val root = circus.keys.first { circus[it]!!.parent == null }

        findProblem(circus[root]!!)

        return ""
    }
}

fun main() {
    val day = Day7(readFullText("_2017/d7/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day7(readFullText("_2017/d7/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}