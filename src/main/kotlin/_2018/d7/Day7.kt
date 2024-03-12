package _2018.d7

import util.Day
import util.findAllMatch
import util.readFullText
import kotlin.system.measureNanoTime

class Worker(var workOn: Char, var timeLeft: Int) {
    fun isWorking() = timeLeft > 0

    fun work() : Boolean {
        timeLeft--
        return timeLeft == 0
    }
}

class Day7(override val input: String) : Day<Long>(input) {
    private var graph = ('A'..'Z').associateWith { mutableListOf<Char>() }.toMutableMap()

    init {
        input.split("\n").forEach { line ->
            val match = line.findAllMatch("""[A-Z] """)
            graph[match.last().first()]?.add(match.first().first())
        }
    }

    override fun solve1(): Long {
        /*var letters = ""

        while (letters.length != 6) {
            val entry = graph.entries.first { it.value.isEmpty() }
            letters += entry.key
            graph.values.forEach { it.remove(entry.key) }
            graph.remove(entry.key)
        }*/

        return -1
    }

    override fun solve2(): Long {
        val workers = List(5) { Worker(' ', 0) }
        var letters = ""
        var seconds = 0

        while (letters.length != 26) {
            val entries = graph.entries.filter { entry -> entry.value.isEmpty() && workers.all { it.workOn != entry.key } }
            val availableWorkers = workers.filter { !it.isWorking() }.toMutableList()

            for (entry in entries) {
                if (availableWorkers.isNotEmpty()) {
                    val worker = availableWorkers.removeFirst()
                    worker.workOn = entry.key
                    worker.timeLeft = (entry.key.code - 'A'.code + 1 + 60)
                }
            }

            for (worker in workers) {
                if (worker.work()) {
                    letters += worker.workOn
                    graph.values.forEach { it.remove(worker.workOn) }
                    graph.remove(worker.workOn)
                }
            }

            seconds++
        }

        return seconds.toLong()
    }
}

fun main() {
    val day = Day7(readFullText("_2018/d7/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day7(readFullText("_2018/d7/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}