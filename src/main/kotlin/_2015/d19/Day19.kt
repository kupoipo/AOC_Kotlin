package _2015.d19

import util.Day
import util.allIndexOf
import util.readFullText
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

// """190-257"""

class Day19(override val input: String) : Day<Long>(input) {
    var transitions = mutableMapOf<String, MutableList<String>>()
    var reverse = mutableMapOf<String, String>()
    var initialMolecule: String

    init {
        input.split("\n\n").let {
            it[0].split("\n").forEach { line ->
                line.split(" => ").let { (origin, transformation) ->
                    transitions.getOrPut(origin) { mutableListOf() }.add(transformation)
                    reverse[transformation] = origin

                }
            }

            initialMolecule = it[1]
        }
    }

    fun nextGeneration(molecule: String) : Set<String> {
        val newMolecules = mutableSetOf<String>()

        transitions.forEach { (origin, listTransformation) ->
            listTransformation.forEach { transformation ->
                molecule.allIndexOf(origin).forEach { index ->
                    (molecule.substring(0, index) + transformation + molecule.substring(index + origin.length)).let {
                        newMolecules.add(it)
                    }
                }
            }
        }

        return newMolecules
    }

    override fun solve1(): Long = nextGeneration(initialMolecule).size.toLong()


    override fun solve2(): Long {
        val toTest = mutableListOf<String>()
        val visited = mutableListOf<String>()

        toTest.add("e")

        while (toTest.isNotEmpty()) {
            val t = toTest.removeFirst()

            if (t == initialMolecule) {
                println(t)
            }

            nextGeneration(t).forEach {
                if (it.length <= initialMolecule.length && it !in toTest && it !in visited)
                    toTest.add(it)
            }


        }

        return 0
    }
}

fun main() {
    val day = Day19(readFullText("_2015/d19/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day19(readFullText("_2015/d19/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}