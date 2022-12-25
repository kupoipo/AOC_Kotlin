package _2022.d16
/*
data class Valve(val name : String, val flow : Int) {
    var adjacentsValve = mutableMapOf<Valve, Int>()
    var opened = false

    fun getDist(otherValve : String) : Int =
         adjacentsValve.keys.find { it.name == otherValve }?.let {  adjacentsValve[it]!! } ?: -1

    override fun util.toString(): String { StringBuilder("")
        return StringBuilder("$name : {").apply {
            adjacentsValve.forEach{ (valve, dist) -> run { append(valve.name + " - " + dist + ", ") } }
        }.append("}\n").util.toString()
    }

    companion object {
        val allValves = read(util.readInput("_2022.d16/test"))
        fun read(content: List<String>) : MutableMap<String, Valve> {
            var res = mutableMapOf<String, Valve>()
            var adjacents = mutableMapOf<String, List<String>>()

            content.forEach { line ->
                run {
                    val (name, flow) = line.substring(6..7) to line.substringAfter("rate=").takeWhile { c -> c != ';' }
                        .toInt()
                    val neighbors =
                        line.substring(line.indexOf("to ") + 9).split(", ").map { it.replace(" ", "") }.toMutableList()

                    adjacents.put(name, neighbors)
                    res.put(name, Valve(name, flow))
                }
            }

            // Pairing Node to is real adjacent Node
            for ((valve, adj) in adjacents) {
                adj.forEach {
                    res[valve]!!.adjacentsValve.put(res[it]!!, 1)
                }
            }

            // Compute all edges and remove nodes which as a flow value at 0
            var toDel = mutableListOf<Valve>()
            for (valve in res.values.filter { it.flow == 0 }) {
                for ((v1, dist1) in valve!!.adjacentsValve) {
                    for ((v2, dist2) in valve!!.adjacentsValve) {
                        if (v1 != v2 && v2 !in v1.adjacentsValve) {
                            v1.adjacentsValve[v2] = dist1 + dist2
                            v1.adjacentsValve.remove(valve)
                            toDel.add(valve)
                        }
                    }
                }
            }
            toDel.filter{ it.name != "AA" }.forEach { res.remove(it.name) }


            return res
        }
    }
}

var path = mutableListOf<String>()

fun solve(valve: Valve, size: Int, time: Int, flowWhenArrived : Int) {
    if (path.distinct().size == size || time <= 15) {
      //  println(path.util.toString() + "  -  " + flowOf(path))
        return
    }

    valve.adjacentsValve.keys.sortedByDescending { path.count { s -> s.equals(it.name) }  }

    for ((adjValve, timePath) in valve.adjacentsValve) {
        path.add(adjValve.name)
        solve(adjValve, size, time - timePath, flowWhenArrived)
        path.removeLast()

    }
}

fun part1() : String {
    var graph = Valve.allValves

    solve(graph["AA"]!!, graph.size, 30, 0)

    return ""
}



fun part2(content : List<String>) : String {

    return ""
}



fun main() {
    var content = util.readInput("_2022.d16/input")

    println(Valve.allValves)

    println(part1())
    println(part2(content))
}*/