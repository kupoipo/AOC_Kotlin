package _2016.d10

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Bot(var lowTo: Int, var highTo: Int, var lowToOutput: Boolean = false, var highToOutput: Boolean = false) {
    val microchips = mutableListOf<Int>()

    fun addMicroShip(value: Int) {
        if (microchips.size == 2) return

        microchips.add(value)

        if (microchips.size == 2) {
            if (lowToOutput) {
                output[lowTo] = microchips.min()
            } else {
                bots[lowTo].addMicroShip(microchips.min())
            }

            if (highToOutput) {
                output[highTo] = microchips.max()
            } else {
                bots[highTo].addMicroShip(microchips.max())
            }
        }
    }

    companion object {
        val bots = MutableList(250) { Bot(0,0) }
        val output = MutableList(50) { 0 }
    }
}

class Day10(override val input: String) : Day<Long>(input) {
    init {
        input.split("\n").sorted().forEach {
            val ints = it.allInts()

            if (it.contains("value")) Bot.bots[ints.last()].addMicroShip(ints.first())
            else {
                val bot = Bot.bots[ints.first()]
                bot.lowTo = ints[1]
                bot.highTo = ints[2]

                if (it.contains("low to output")) bot.lowToOutput = true
                if (it.contains("high to output")) bot.highToOutput = true
            }
        }
    }

    override fun solve1(): Long = Bot.bots.indices.first { Bot.bots[it].microchips.containsAll(listOf(17, 61)) }.toLong()

    override fun solve2(): Long {
        return (Bot.output[0] * Bot.output[1] * Bot.output[2]).toLong()
    }
}

fun main() {
    val day = Day10(readFullText("_2016/d10/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day10(readFullText("_2016/d10/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}