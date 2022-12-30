package _2021.d18

import util.Day
import util.Packet.*
import util.parseLineWithMultipleList
import util.readFullText
import kotlin.system.measureTimeMillis
class Day18(override val input : String) : Day<Long>(input) {
    val lines : List<ListPacket> = input.split("\n").map { parseLineWithMultipleList(it) } as List<ListPacket>

    fun firstAtDepth(list : ListPacket, i: Int): MutableList<ListPacket> {
        val res = mutableListOf<ListPacket>()

        if (i == 0) {
            res.add(list)
            return res
        }

        val depth = i - 1

        list.value.forEach {
            if (it is ListPacket) {
                val resDepth = firstAtDepth(it, depth)

                if (resDepth.isNotEmpty()) {
                    resDepth.add(list)
                    return resDepth
                }
            }
        }

        return res
    }

    fun explode(list : ListPacket) {
        // List with hiearchy from the list to split to the whole list
        // -----------------------------------------------------------
        var pathToSplit = if (firstAtDepth(list, 5).isNotEmpty()) firstAtDepth(list, 5) else firstAtDepth(list, 4)

        // [4,5]
        var listToRemove = pathToSplit.removeFirst()
        var listSearched = listToRemove

        var (x, y) = listSearched.let { it[0] as NumberPacket to it[1] as NumberPacket }

        // [0, [4,5], [4, 8]]
        val listToRemoveFrom = pathToSplit.removeFirst()
        var currentList      = listToRemoveFrom

        // 1
        var indexToExplode = currentList.value.indexOf(listSearched)

        when (indexToExplode) {
            0 -> {
                currentList[1].incrementFirst(y)

                while (pathToSplit.isNotEmpty()) {
                    listSearched = currentList // [4, [9, 8]]
                    currentList = pathToSplit.removeFirst() // [[4, [9, 8]], [0, 0]]

                    indexToExplode = currentList.value.indexOf(listSearched)

                    if (indexToExplode > 0) {
                        currentList[indexToExplode - 1].incrementLast(x)
                        break
                    }
                }

                listToRemoveFrom.value.add(0,NumberPacket(0))

            }

            currentList.value.size - 1 -> {
                currentList[currentList.value.size - 2].incrementLast(x)

                while (pathToSplit.isNotEmpty()) {
                    listSearched = currentList
                    // [4, [9, 8]]
                    currentList = pathToSplit.removeFirst() // [[4, [9, 8]], [0, 0]]

                    indexToExplode = currentList.value.indexOf(listSearched)

                    if (indexToExplode < currentList.value.size - 1) {
                        currentList[indexToExplode + 1].incrementFirst(y)
                        break
                    }
                }

                listToRemoveFrom.value.add(NumberPacket(0))

            }

            else -> {
                currentList[indexToExplode + 1].incrementFirst(y)
                currentList[indexToExplode - 1].incrementLast(x)
            }
        }

        listToRemoveFrom.value.remove(listToRemove)
    }

    fun split(list : ListPacket) : Boolean {
        for (index in list.value.indices) {
            val it = list[index]
            when (it) {
                is NumberPacket -> {
                    if (it.value >= 10) {
                        list.value.remove(it)
                        if (it.value % 2 == 0) {
                            list.value.add(
                                index,
                                ListPacket(mutableListOf(NumberPacket(it.value / 2), NumberPacket(it.value / 2)))
                            )
                        } else {
                            list.value.add(
                                index,
                                ListPacket(mutableListOf(NumberPacket(it.value / 2), NumberPacket(it.value / 2 + 1)))
                            )
                        }
                    }

                    return true
                }

                is ListPacket -> {
                    if (split(it)) {

                    }
                }
            }
        }

        return false
    }

    fun reduce(list : ListPacket) : ListPacket {
        println(list)


        while (true) {
            println("Before explode : $list")
            while (list.depth() > 4) {
                explode(list)
            }
            println("After explode  : $list")
            if (split(list)) {
                continue
            }

            break
        }
        println(list.toString() + " - " + list.depth())

        return list
    }

    override fun solve1(): Long {
        var res = lines[0] + lines[1]

        res = reduce(lines[0] as ListPacket)

        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}


fun main() {
    //var day = Day18(readFullText("_2021/d18/test"))
    var day = Day18(readFullText("_2021/d18/test"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}