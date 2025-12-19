package _2025.d10

import _2025.d10.MachineP1.Day10StatePart1
import util.*
import kotlin.system.measureNanoTime


data class MachineP1(var machine: Int, val operationsAsInt: List<Int>, val goal: Int) {
    private class Day10StatePart1(parent: Day10StatePart1?, val machine: MachineP1, val path: Int, time: Int) :
        State(parent, time) {
        override fun isDeadLock(): Boolean = false

        override fun nextStates(): MutableList<State> = List(machine.operationsAsInt.size) { i ->
            Day10StatePart1(this, machine.toggle(i), i, time + 1)
        }.toMutableList()

        override fun isGoal(): Boolean = machine.machine == machine.goal

        override fun equals(other: Any?): Boolean {
            if (other !is Day10StatePart1) return false

            return machine.machine == other.machine.machine
        }

        override fun hashCode(): Int = machine.machine
    }

    fun toggle(index: Int): MachineP1 {
        return MachineP1(machine xor operationsAsInt[index], operationsAsInt, goal)
    }

    fun solve(): List<Int> {
        val from = Day10StatePart1(null, this, -1, 0)
        val path = State.shortestPastFrom(from)?.rebuildPath()?.map {
            if (it == null) 0
            else {
                it as Day10StatePart1; it.path
            }
        }?.dropLast(2)
        return path ?: emptyList()
    }

    override fun toString(): String {
        return machine.toString(2)
    }
}

private class MachineP2(val operations: List<List<Int>>, val goal: List<Int>) {
    val goalSorted = goal.mapIndexed { i, iGoal -> i to iGoal }.sortedBy { -it.second }

    fun maxPress(current: MutableList<Int>, operation: List<Int>): Int {
        if (operation.any { current[it] >= goal[it] }) return -1

        return operation.minOf { goal[it] - current[it] }
    }

    fun solveRecu(current: MutableList<Int>, press: MutableList<Int>): List<Int>? {
        if (current == goal) return press;

        val target = goalSorted.first { current[it.first] == 0 }
        val buttons = operations.filter { it.contains(target.first) }
        
        buttons.allArrangement(1)
        
        for (operation in operations.indices) {
            val nbPress = maxPress(current, current)

            if (nbPress != -1) {
                for (nb in nbPress downTo 1) {
                    val res = solveRecu(current.toMutableList().apply {
                        for (i in current) {
                            this[i] += nb
                        }
                    }, press.apply { press[0] += nb })

                    if (res != null) return press

                    press[0] -= nb
                }
            }
        }

        return null
    }

    fun solve(): List<Int> {
        return solveRecu(goal.map { 0 }.toMutableList(), operations.map { 0 }.toMutableList()) ?: listOf()
    }

}

class Day10(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val machinesP1 = mutableListOf<MachineP1>()
    private val machinesP2 = mutableListOf<MachineP2>()

    init {
        input.split("\n").forEach { line ->
            val goal = line.findAllMatch("""\[(#|\.)+\]""").first().drop(1).dropLast(1)
            val buttons = line.findAllMatch("""\((\d+,|\d+)+\)""").map { it.allInts() }
            val joltages = line.findAllMatch("""\{(\d+,|\d+)+\}""").first().allInts()

            val goalToInt = goal.replace("#", "1").replace(".", "0").toInt(2)
            val buttonsToInt = buttons.map {
                val binary = goal.map { '0' }.toMutableList()
                it.forEach { i -> binary[i] = '1' }
                binary.joinToString("").toInt(2)
            }

            machinesP1.add(MachineP1(0, buttonsToInt, goalToInt))
            machinesP2.add(
                MachineP2(
                    buttons,
                    joltages
                )
            )
        }
    }


    override fun solve1(): Long = machinesP1.sumOf { it.solve().size }.toLong()

    override fun solve2(): Long {

        return machinesP2.map { val res = it.solve(); println(res); res }.sumOf { it.size }.toLong()
    }
}

fun main() {
    var day: Day10
    println("Temps construction : ${measureNanoTime { day = Day10(false, readFullText("_2025/d10/input")) } / 1e9}s")
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            var dayTest: Day10
            println("Test $i")
            println("Temps construction : ${measureNanoTime { dayTest = Day10(false, readFullText("_2025/d10/test$i")) } / 1e9}s")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}