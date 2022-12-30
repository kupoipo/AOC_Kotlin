package util

import java.util.PriorityQueue

/**
 * Class state which represent a node in a graph.
 * For using this class you need to :
 *    - Create your own class which extends State
 *    - Add in your own class parameters that are useful, those parameters are used to determine if a state is already used or not
 *    - Override function
 */
abstract class State(open var parent : State? = null, open var time : Int = 0) {
    abstract fun isDeadLock(): Boolean

    abstract fun nextStates() : MutableList<State>

    abstract fun isGoal() : Boolean

    open fun timeToGoal() : Int = time
    fun rebuildPath() {
        var current : State? = this

        while (current != null) {
            println(current)
            current = current.parent
        }
    }

    companion object {
        /**
         * Weight for the WA* algorithm
         */
        var weight : Int = 1

        /**
         * For using this function, no need to override timeToGoal
         */
        fun allPathTo(from : State) : MutableList<State> {
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
        fun shortestPastFrom(from : State) : State? {
            val comparator: Comparator<State> = compareBy { it.timeWeighed() }
            val queue = PriorityQueue(comparator)
            val visited = mutableSetOf<State>()

            queue.add(from)

            while (queue.isNotEmpty()) {
                val current = queue.poll()

                if (current.isGoal()) return current

                visited.add(current)
                current.nextStates().forEach { nextState ->
                    if (!(nextState in visited || existQueueLessCost(queue, nextState))) {
                        queue.add(nextState)
                    }

                }
            }

            return null
        }

        private fun existQueueLessCost(queue: PriorityQueue<State>, nextState: State): Boolean {
            queue.forEach {
                if (nextState == it && it.timeWeighed() < nextState.timeWeighed()) {
                    return true
                }
            }

            return false
        }
    }

    private fun timeWeighed() : Int {
        return this.timeToGoal() * weight
    }


}

data class Node<T>(val value : T) {
    val adjacentsNodes = mutableMapOf<Node<T>, Int>()

    fun addNode(node : Node<T>, cost : Int = 1) {
        adjacentsNodes[node] = 1
    }

    fun getAdjacentsNodes() : MutableList<Node<T>>{
        return adjacentsNodes.keys.toMutableList()
    }

    override fun toString(): String {
        var res = ""
        res += "Value : $value - "
        res += "Lead to "

        adjacentsNodes.forEach { t, u ->
            res += "[${t.value} : $u], "
        }

        return res.dropLast(1) + "\n"


    }
}


