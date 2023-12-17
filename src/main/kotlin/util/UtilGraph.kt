package util

import _2016.d13.MazeCubicleState
import _2016.d22.DFState
import _2023.d17.LavaState
import java.util.PriorityQueue

/**
 * Class state which represent a node in a graph.
 * For using this class you need to :
 *    - Create your own class which extends State
 *    - Add in your own class parameters that are useful, those parameters are used to determine if a state is already used or not
 *    - Override function
 */
abstract class State(open var parent: State? = null, open var time: Int = 0) {
    abstract fun isDeadLock(): Boolean

    abstract fun nextStates(): MutableList<State>

    abstract fun isGoal(): Boolean

    open fun timeToGoal(): Int = time
    fun rebuildPath(print: Boolean = false): List<State?> {
        val res = mutableListOf<State?>(this)
        var current: State? = this

        while (current != null) {
            if (print)
                println(current)

            current = current.parent
            res.add(current)
        }

        return res
    }

    companion object {
        /**
         * Weight for the WA* algorithm
         */
        var weight: Int = 1

        /**
         * For using this function, no need to override timeToGoal
         */
        fun allPathTo(from: State): MutableList<State> {
            if (from.isDeadLock())
                return mutableListOf()

            if (from.isGoal())
                return mutableListOf(from)

            val paths = mutableListOf<State>()

            from.nextStates().forEach {
                val currentPaths = allPathTo(it)
                if (currentPaths.isNotEmpty())
                    paths.addAll(currentPaths)
            }

            return paths
        }

        /**
         * Need to override timeToGoal in the specific case where this algorithm is used
         */
        fun shortestPastFrom(from: State): State? {
            val comparator: Comparator<State> = compareBy { it.timeWeighed() }
            val queue = PriorityQueue(comparator)
            val queueCost = mutableMapOf<State, Int>()
            val visited = mutableSetOf<State>()

            queue.add(from)

            while (queue.isNotEmpty()) {
                val current = queue.poll()

                visited.add(current)

                current.nextStates().forEach { nextState ->
                    if (nextState.isGoal()) return nextState

                    if (!(nextState in visited || nextState.isDeadLock())) {

                        if (queueCost[nextState] == null) {
                            queueCost[nextState] = nextState.timeWeighed()
                            queue.add(nextState)
                        } else {
                            if (queueCost[nextState]!! > nextState.timeWeighed()) {
                                queueCost[nextState] = nextState.timeWeighed()
                                queue.add(nextState)
                            }
                        }
                    }
                }
            }

            return null
        }

    }

    open fun timeWeighed(): Int {
        return this.timeToGoal() * weight
    }


}

data class Node<T>(val value: T) {
    val adjacentNodes = mutableMapOf<Node<T>, Int>()

    fun addNode(node: Node<T>, cost: Int = 1) {
        adjacentNodes[node] = cost
    }

    fun getAdjacentsNodes(): MutableList<Node<T>> {
        return adjacentNodes.keys.toMutableList()
    }

    override fun toString(): String {
        var res = ""
        res += "Value : $value - "
        res += "Lead to "

        adjacentNodes.forEach { t, u ->
            res += "[${t.value} : $u], "
        }

        return res.dropLast(1) + "\n"


    }
}


