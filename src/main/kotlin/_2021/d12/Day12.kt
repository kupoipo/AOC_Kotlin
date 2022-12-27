package _2021.d12

import util.Day
import util.Node
import util.State
import util.readFullText
import kotlin.system.measureTimeMillis

data class Day12State(val node : Node<String>, val path : List<String> = listOf()) : State() {

    fun existSmallTwiceOtherThan() : Boolean {
        path.forEach { str ->
            if (str[0] in 'a'..'z' && str != node.value && path.count { it == str } == 2) {
                return true
            }
        }

        return false
    }

    override fun isOver(): Boolean {
        if (node.value[0] in 'a'..'z') {

            // Si deux fois par start
            // ----------------------
            if (node.value == "start" && path.count{it == node.value} == 1) {
                    return true
            }

            // Si trois fois par un noeud miniature
            // ------------------------------------
            if (path.count{it == node.value} == 2)
                return true

            // Si deux fois par un minuscule alors qu'il existe deja
            // -----------------------------------------------------
            if ((path.count{it == node.value} == 1) && (existSmallTwiceOtherThan()))
                return true
        }
        return false
    }

    override fun nextStates(): MutableList<State> {
        val res = mutableListOf<State>()
        node.getAdjacentsNodes().map {
            res.add(Day12State(it, path + node.value))
        }

        return res
    }

    override fun isGoal(): Boolean {
        return node.value == "end"
    }

    override fun toString(): String {
        return "\n" + path.joinToString(separator = ",") + ",end"
    }
}

class Day12(override val input : String) : Day<Long>(input) {
    val lines = input.split("\n")
    val nodes = mutableMapOf<String, Node<String>>()

    fun create(name : String) {
        if (nodes[name] == null) nodes[name] = Node(name)
    }

    init {
        lines.forEach {
            it.split("-").let {
                create(it[0])
                create(it[1])

                nodes[it[0]]!!.addNode(nodes[it[1]]!!)
                nodes[it[1]]!!.addNode(nodes[it[0]]!!)
            }
        }
    }

    override fun solve1(): Long {
        return State.allPathTo(Day12State(nodes["start"]!!)).size.toLong()
    }
    override fun solve2(): Long {
        print(nodes["b"])
        return -1
    }
}

fun main() {
    //var day = Day12(readFullText("_2021/d12/test"))
    var day = Day12(readFullText("_2021/d12/input"))


    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}