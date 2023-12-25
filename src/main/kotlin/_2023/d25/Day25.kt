package _2023.d25

import util.Day
import util.findAllMatch
import util.random
import util.readFullText
import kotlin.system.measureNanoTime
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DefaultUndirectedGraph

/**
 * Thanks to https://github.com/eagely/adventofcode for the library.
 */
class Day25(override val input : String) : Day<Long>(input) {
    val graph = DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge::class.java)
    init {
        input.split("\n").map { it.findAllMatch("""\w+""") }.forEach {
            val origin = it.first()
            graph.addVertex(origin)

            for (linked in it.drop(1)) {
                graph.addVertex(linked)
                graph.addEdge(origin, linked)
            }
        }
    }

    override fun solve1(): Long {
        val minCut = StoerWagnerMinimumCut(graph).minCut()
        println(minCut)
        graph.removeAllVertices(minCut)
        return (graph.vertexSet().size * minCut.size).toLong()
    }
    override fun solve2(): Long {
        return -1
    }

}

fun main() {
    val day = Day25(readFullText("_2023/d25/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day25(readFullText("_2023/d25/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}