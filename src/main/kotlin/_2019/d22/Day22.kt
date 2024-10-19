
package _2019.d22

import _2021.d18.split
import util.Day
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime
class Day22(private val isTest: Boolean, override val input : String) : Day<Long>(input) {


    interface Dealing {
        fun deal(deck: MutableList<Int>): MutableList<Int>
    }

    class Cut(val index: Int) : Dealing {
        override fun deal(deck: MutableList<Int>): MutableList<Int> {
            val take = mutableListOf<Int>()
            if (index < 0) {
                for (i in 0 until  index * -1) {
                    take.add(0, deck.removeLast())
                }

                take.addAll(deck)
                return take
            } else {
                for (i in 0 until  index ) {
                    take.add(deck.removeFirst())
                }

                deck.addAll(take)
                return deck
            }
        }
    }

    class DealWithIncrement(val index: Int) : Dealing {
        override fun deal(deck: MutableList<Int>): MutableList<Int> {
            var index = 0
            val res = MutableList(deck.size) { 0 }

            while (deck.isNotEmpty()) {
                res[index] = deck.removeFirst()
                index = (index + this.index) % res.size
            }

            return res
        }
    }

    class NewStack: Dealing {
        override fun deal(deck: MutableList<Int>): MutableList<Int> = deck.reversed().toMutableList()
    }

    private val deck = MutableList ( if (isTest) 10 else 10007) { it }
    private val instructions = input.split("\n").map {
        if (it.contains("cut")) Cut(it.firstInt())
        else {
            if (it.contains("with")) DealWithIncrement(it.firstInt())
            else NewStack()
        }
    }
    override fun solve1(): Long = instructions.fold(deck) { acc, inst -> inst.deal(acc) }.indexOfFirst { it == if (isTest) 6 else 2019 }.toLong()
    override fun solve2(): Long {
        val deck = MutableList (119315717514047.toInt()) { it }
        return -1
    }
}

fun main() {
    val day = Day22(false, readFullText("_2019/d22/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day22(true, readFullText("_2019/d22/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}