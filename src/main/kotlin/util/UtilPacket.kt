package util

import java.lang.Exception


sealed interface Packet : Comparable<Packet> {

    data class NumberPacket(var value: Int) : Packet {
        override fun get(i: Int): Packet {
            throw Exception("NumberPacket don't provide get access")
        }

        override fun plus(other: Packet): Packet = when (other) {
            is NumberPacket -> NumberPacket(this.value + other.value)
            is ListPacket -> ListPacket(mutableListOf(this, other))
        }

        override fun plus(other: Int): Packet = NumberPacket(this.value + other)
        override fun depth(): Int = 0

        override fun incrementLast(x: NumberPacket) {
            this.value += x.value
        }

        override fun incrementFirst(x: NumberPacket) {
            incrementLast(x)
        }

        override fun compareTo(other: Packet): Int = when (other) {
            is NumberPacket -> this.value compareTo other.value
            is ListPacket -> this.toListPacket() compareTo other
        }

        override fun toString(): String {
            return this.value.toString()
        }
    }

    data class ListPacket(val value: MutableList<Packet>) : Packet {
        override infix fun compareTo(other: Packet): Int {
            return when (other) {
                is NumberPacket -> this compareTo other.toListPacket()
                is ListPacket -> {
                    this.value.zip(other.value) { a, b -> a compareTo b }.forEach { if (it != 0) return it }
                    return this.value.size - other.value.size
                }
            }
        }
        override fun toString(): String = value.toString()

        override fun depth() : Int {
            var depth = 1
            var max = 0
            value.forEach {
                if (it is ListPacket) {
                    val recuDepth = it.depth()
                    if (recuDepth > max) {
                        max = recuDepth
                    }
                }
            }

            return depth + max
        }

        override fun incrementLast(x: NumberPacket) {
            value.last().incrementLast(x)
        }

        override fun incrementFirst (x: NumberPacket) {
            value.first().incrementFirst(x)
        }

        override operator fun get(i : Int) : Packet {
            return value[i]
        }

        override fun plus(other: Packet): Packet = ListPacket(mutableListOf(ListPacket(value.toMutableList()), other))
        override fun plus(other: Int): Packet = ListPacket(value.toMutableList().apply { add(NumberPacket(other)) })
        fun onlyNumber(): Boolean = value.count { it is ListPacket } == 0
    }


    fun NumberPacket.toListPacket() = ListPacket(mutableListOf(this))
    operator fun get(i: Int): Packet

    operator fun plus(other : Packet) : Packet

    operator fun plus(other : Int) : Packet

    fun depth() : Int
    fun incrementLast(x: NumberPacket)
    fun incrementFirst(x: NumberPacket)
}


fun parseLineWithMultipleList(line: String): Packet? {
    if (line.isEmpty()) {
        return null
    }
    if (line[0].isDigit()) {
        return Packet.NumberPacket(line.toInt())
    }

    var bracketCount = 0
    var lastComma = 0

    val packets = mutableListOf<Packet?>()

    line.forEachIndexed { i, c ->
        when (c) {
            '[' -> bracketCount++
            ']' -> {
                bracketCount--
                if (bracketCount == 0) {
                    packets += parseLineWithMultipleList(line.take(i).drop(lastComma + 1))
                }
            }

            ',' -> {
                if (bracketCount == 1) {
                    packets += parseLineWithMultipleList(line.take(i).drop(lastComma + 1))
                    lastComma = i
                }
            }
        }
    }

    return Packet.ListPacket(packets.filterNotNull().toMutableList())
}
