package _2018.d4

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class DateTime(private val month: Int, private val day: Int, private val hour: Int, val minute: Int) :
    Comparable<DateTime> {
    override fun compareTo(other: DateTime): Int {
        if (month != other.month) return month.compareTo(other.month)
        if (day != other.day) return day.compareTo(other.day)
        if (hour != other.hour) return hour.compareTo(other.hour)
        if (minute != other.minute) return minute.compareTo(other.minute)
        return 0
    }

    override fun toString(): String {
        return "[$month-$day ${hour}:$minute]"
    }


}

class Instruction(val dt: DateTime, val instruction: String) {
    override fun toString(): String {
        return "$dt $instruction\n"
    }
}

class Day4(override val input: String) : Day<Long>(input) {

    val instructions = input.split("\n").map { line ->
        Instruction(
            line.replace("-", "_").allInts().let { DateTime(it[1], it[2], it[3], it[4]) },
            line.substring(line.lastIndexOf("]") + 2)
        )
    }.sortedBy { it.dt }
    private var sleepScheduleMap = mutableMapOf<Int, Map<Int, Int>>()

    init {
        val sleepSchedule = mutableMapOf<Int, MutableList<Int>>()
        var currentList = mutableListOf<Int>()
        var lastMinute = 0
        for (i in instructions) {
            if (i.instruction.contains("Guard")) {
                currentList = sleepSchedule.getOrPut(i.instruction.allInts().first()) { mutableListOf() }
            }

            if (i.instruction.contains("asleep")) {
                lastMinute = i.dt.minute
            }

            if (i.instruction.contains("wakes up")) {
                var currentMinute = lastMinute

                while (currentMinute != i.dt.minute) {
                    currentList.add(currentMinute)
                    currentMinute = (currentMinute + 1) % 60
                }
            }
        }
        for ( (a,b) in sleepSchedule) {
            sleepScheduleMap[a] = b.groupingBy { it }.eachCount()
        }
        sleepScheduleMap = sleepScheduleMap.filter { it.value.isNotEmpty() }.toMutableMap()
    }

    override fun solve1(): Long {
        val entry = sleepScheduleMap.maxBy { it.value.values.sum() }
        val minuteMax = entry.value.maxBy { it.value }.key
        return (entry.key * minuteMax).toLong()
    }

    override fun solve2(): Long {
        val max = sleepScheduleMap.maxOf { guard -> guard.value.maxOf { minutes -> minutes.value } }
        val entry = sleepScheduleMap.filter { it.value.values.contains(max) }.entries.first()

        return entry.key.toLong() * entry.value.entries.first { it.value == max }.key
    }
}

fun main() {
    val day = Day4(readFullText("_2018/d4/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day4(readFullText("_2018/d4/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}