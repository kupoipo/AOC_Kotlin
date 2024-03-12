package _2018.d8

import util.Day
import util.readFullText
import java.util.*
import kotlin.system.measureNanoTime

enum class StateDay8 {
    CREATE, READ_METADATA, ASSIGN_METADATA
}

class NodeDay8(private val label: Int, val nbChild: Int, var nbMetadata: Int = 0) {
    val listChild = mutableListOf<NodeDay8>()
    val listMetadata = mutableListOf<Int>()

    override fun toString(): String {
        return "$label : $listMetadata"
    }

    fun weight() : Long {
        return if (nbChild == 0) {
            listMetadata.sum().toLong()
        } else {
            listMetadata.sumOf { if (it <= listChild.size) listChild[it- 1].weight() else 0 }
        }
    }

    companion object {
        var nodes = mutableListOf<NodeDay8>()
    }
}

class Day8(override val input : String) : Day<Long>(input) {
    private var currentLabel = 0
    private val pile = LinkedList<NodeDay8>()

    init {
        NodeDay8.nodes.clear()
        var state = StateDay8.CREATE

        input.split(" ").forEach {
            when (state) {
                StateDay8.CREATE -> {
                    val newNode = NodeDay8(currentLabel, it.toInt())
                    NodeDay8.nodes.add(newNode)

                    if (pile.isNotEmpty())
                        pile.last.listChild.add(newNode)

                    pile.add(newNode)
                    currentLabel++
                    state = StateDay8.ASSIGN_METADATA
                }

                StateDay8.ASSIGN_METADATA -> {
                    pile.last.nbMetadata = it.toInt()
                    state = if (pile.last.nbChild == 0) StateDay8.READ_METADATA else StateDay8.CREATE
                }

                else -> {
                    pile.last.listMetadata.add(it.toInt())

                    if (pile.last.listMetadata.size == pile.last.nbMetadata) {
                        pile.removeLast()
                        state = if (pile.isNotEmpty() && pile.last.nbChild == pile.last.listChild.size) StateDay8.READ_METADATA else StateDay8.CREATE
                    }
                }
            }
        }
    }

    override fun solve1(): Long = NodeDay8.nodes.sumOf { it.listMetadata.sum() }.toLong()

    override fun solve2(): Long = NodeDay8.nodes.first { parent -> NodeDay8.nodes.all { child -> !child.listChild.contains(parent) } }.weight()
}

fun main() {
    val day = Day8(readFullText("_2018/d8/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day8(readFullText("_2018/d8/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}