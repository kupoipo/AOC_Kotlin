package _2017.d24

import util.Day
import util.allLong
import util.readFullText
import kotlin.math.max
import kotlin.system.measureNanoTime


class Day24(override val input: String) : Day<Long>(input) {
    class Bridge(val start: Long, val end: Long) {
        override fun equals(other: Any?): Boolean {
            if (other == null)
                return false
            if (other.javaClass != this.javaClass)
                return false

            val otherBridge = other as Bridge

            return (start == otherBridge.start && end == otherBridge.end) || (end == otherBridge.start && start == otherBridge.end)
        }

        fun reverse() = Bridge(end, start)

        override fun toString(): String {
            return "$start/$end"
        }
    }


    private val setOfBridges = input.split("\n").map { line -> line.allLong().let { Bridge(it.first(), it.last()) } }
    private val bridges: MutableMap<Long, MutableList<Bridge>> = mutableMapOf()

    init {
        setOfBridges.forEach { bridge ->
            bridges.getOrPut(bridge.start) { mutableListOf() }.add(bridge)
            bridges.getOrPut(bridge.end) { mutableListOf() }.add(bridge)
        }

        assert(setOfBridges.size == bridges.values.sumOf { it.size } * 2)
    }

    private var maxSize = 0L
    private var maxLength = 0L

    private fun maxBridge(currentBridge: MutableList<Bridge>, maxFunction: (List<Bridge>) -> Unit) {
        val nextPart = if (currentBridge.isEmpty()) {
            buildSet {
                addAll(setOfBridges.filter { it.start == 0L })
                for (bridge in setOfBridges.filter { it.end == 0L }) {
                    add(bridge.reverse())
                }
            }
        } else {
            bridges[currentBridge.last().end]?.filter { !currentBridge.contains(it) } ?: setOf()
        }

        for (b in nextPart) {
            maxBridge(currentBridge.toMutableList().apply {
                if (this.isEmpty() || b.start == this.last().end)
                    add(b)
                else
                    add(b.reverse())
            }, maxFunction)

        }

        maxFunction(currentBridge)
    }


    override fun solve1(): Long {
        maxBridge(mutableListOf()) { newBridge ->
            val localSize = newBridge.sumOf { it.start + it.end }
            if (localSize > maxSize) {
                maxSize = localSize
            }
        }
        return maxSize
    }

    override fun solve2(): Long {
        maxSize = 0L
        maxBridge(mutableListOf()) { newBridge ->
            val localSize = newBridge.size.toLong()
            if (localSize >= maxLength) {
                if (localSize > maxLength) {
                    maxLength = localSize
                    maxSize = newBridge.sumOf { it.start + it.end }
                } else {
                    val newSize = newBridge.sumOf { it.start + it.end }
                    if (newSize > maxSize) {
                        maxSize = newSize
                    }
                }
            }
        }
        return maxSize
    }
}

fun main() {
    val day = Day24(readFullText("_2017/d24/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day24(readFullText("_2017/d24/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}