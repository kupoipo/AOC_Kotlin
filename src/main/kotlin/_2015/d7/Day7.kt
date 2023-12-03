package _2015.d7

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day7(override val input : String) : Day<Long>(input) {
    val mapValue = mutableMapOf<String, Int>()
    val mapOperation = mutableMapOf<String, String>()

    init {
        input.split("\n").map { it.replace("-> ", "") }.forEach {
            mapOperation[it.split(" ").last()] = it.substring(0, it.lastIndexOf(" "))
        }
    }

    fun getValue(destination : String) : Int {
        if (destination.all { char -> char.isDigit() }) return destination.toInt()
        if (mapValue[destination] != null) return mapValue[destination]!!

        val tab = mapOperation[destination]!!.split(" ")

        val operator2 = getValue(tab.last())

        when (tab.size) {
            3 -> {
                val operator1 = getValue(tab.first())
                val operator = tab[1]

                when (operator) {
                    "AND" -> mapValue[destination] = operator1 and operator2
                    "OR" -> mapValue[destination] = operator1 or operator2
                    "RSHIFT" -> mapValue[destination] = operator1 shr operator2
                    "LSHIFT" -> mapValue[destination] = operator1 shl operator2
                    else -> {
                        println("Erreur")
                    }
                }
            }

            2 -> {
                mapValue[destination] = operator2.inv()
            }

            else -> {
                mapValue[destination] = getValue(tab.first())
            }
        }

        return mapValue[destination]!!
    }

    override fun solve1(): Long {
        return 0//getValue("a").toLong()
    }
    override fun solve2(): Long {
        mapValue["b"] = 16076
        return getValue("a").toLong()
    }
}

fun main() {
    //var day = Day7(readFullText("_2015/d7/test"))
    var day = Day7(readFullText("_2015/d7/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day7(readFullText("_2015/d7/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}