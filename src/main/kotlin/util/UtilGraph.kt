package util

abstract class State(val parent : State? = null, val time : Int = 0) {

    abstract fun isOver(): Boolean

    abstract fun nextStates() : MutableList<State>

    abstract fun isGoal() : Boolean

    companion object {
        fun allPathTo(from : State) : MutableList<State> {
            if (from.isOver())
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


