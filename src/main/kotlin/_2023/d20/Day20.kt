package _2023.d20

import _2023.d20.Module.Companion.START_POINT
import _2023.d20.Module.Companion.modules
import d7.list
import util.Day
import util.PPCM
import util.readFullText
import kotlin.system.measureNanoTime

var index = 0L

abstract class Module(val name: String) {
    val connectedModules = mutableListOf<String>()
    val pulseSent = PULSE.values().associateWith { 0 }.toMutableMap()
    abstract fun send(): MutableList<String>

    abstract fun receive(pulse: PULSE, from: String)

    companion object {
        const val START_POINT = "broadcaster"
        val modules = mutableMapOf<String, Module>("rx" to FlipFlop(false, false, "rx"))
        val goingToVF = mutableMapOf<String, Pair<Long, Long>>()

        fun moduleFromString(string: String) {
            val name = string.substring(0, string.indexOf(" ")).let { if (it.first() != 'b') it.drop(1) else it }
            val m: Module =
                if (string.first() in "b%") FlipFlop(false, string.first() == 'b', name) else Conjunction(name)
            m.connectedModules.addAll(
                string.substring(string.indexOf(">") + 1).split(",").map { it.trim() }
            )

            if (m.connectedModules.first() == "vf") {
                goingToVF[m.name] = 0L to 0L
            }

            modules[name] = m
        }
    }

    override fun toString(): String {
        return "$name -> $connectedModules"
    }
}

class FlipFlop(var active: Boolean, val isBroadcast: Boolean, name: String) : Module(name) {
    var mustSend = false
    var toSend: PULSE = PULSE.LOW
    override fun send(): MutableList<String> {
        if (!isBroadcast && !mustSend) return mutableListOf()

        mustSend = false

        connectedModules.forEach { m ->
            pulseSent[toSend] = pulseSent[toSend]!! + 1
            modules[m]!!.receive(toSend, name)
        }
        return connectedModules.toMutableList()
    }

    override fun receive(pulse: PULSE, from: String) {
        if (pulse == PULSE.HIGH) return

        mustSend = true

        toSend = if (active) PULSE.LOW else PULSE.HIGH
        if (isBroadcast)
            toSend = pulse

        active = !active
    }
}

class Conjunction(name: String) : Module(name) {
    val signalFrom = mutableMapOf<String, PULSE>()
    override fun send(): MutableList<String> {
        val s = if (signalFrom.values.all { it == PULSE.HIGH }) PULSE.LOW else PULSE.HIGH

        /**
         * Cycle detection
         */
        if (name in goingToVF.keys && signalFrom.values.first() == PULSE.LOW) {
            if (goingToVF[name]!!.first == 0L) {
                goingToVF[name] = index to 0
            } else if (goingToVF[name]!!.second == 0L) {
                goingToVF[name] = goingToVF[name]!!.first to index - goingToVF[name]!!.first
            }
        }

        connectedModules.forEach { m ->
            pulseSent[s] = pulseSent[s]!! + 1
            modules[m]!!.receive(s, name)
        }

        return connectedModules
    }

    override fun receive(pulse: PULSE, from: String) {
        signalFrom[from] = pulse
    }
}

class Day20(override val input: String) : Day<Long>(input) {
    init {
        input.split("\n").forEach { line -> Module.moduleFromString(line) }
        modules.values.forEach { from ->
            from.connectedModules.forEach { to ->
                if (to != "rx" && modules[to]!! is Conjunction) {
                    (modules[to]!! as Conjunction).signalFrom[from.name] = PULSE.LOW
                }
            }
        }
    }

    fun launch() {
        modules[START_POINT]!!.receive(PULSE.LOW, "button")
        val queue = modules[START_POINT]!!.send()

        while (queue.isNotEmpty()) {
            val mod = queue.removeAt(0)

            queue.addAll(modules[mod]!!.send())
        }
    }

    override fun solve1(): Long {
        repeat(1000) {
            launch()
        }
        return ((modules.values.sumOf { it.pulseSent[PULSE.LOW]!! } + 1000) * modules.values.sumOf { it.pulseSent[PULSE.HIGH]!! }).toLong()
    }

    override fun solve2(): Long {
        while (Module.goingToVF.values.any { it.first == 0L || it.second == 0L }) {
            launch()
            index++
        }

        return PPCM(Module.goingToVF.values.map { it.second })
    }
}

fun main() {
    val day = Day20(readFullText("_2023/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2023/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}


enum class PULSE { HIGH, LOW }
