package _2022.d21
import util.readInput
import kotlin.system.measureTimeMillis

const val HUMAN =  "humn"
const val PRIMARY_MONKEY = "root"
val allValue = mutableMapOf<String, Value>()

class Value(var realValue : Long = Long.MIN_VALUE, val value1 : String = "", val operator : Char? = null, val value2 : String = "") {
    fun get(): Long {
        if (realValue == Long.MIN_VALUE) return allValue[value1]!!.operation(operator, allValue[value2]!!)
        return realValue
    }

    override fun toString(): String = if (realValue != Long.MIN_VALUE) "$realValue\n" else "$value1 $operator $value2\n"

    private fun operation(operator: Char?, value2: Value): Long = when(operator) {
        '+' -> this.get() + value2.get()
        '-' -> this.get() - value2.get()
        '*' -> this.get() * value2.get()
        '/' -> this.get() / value2.get()
        else -> {
            this.get()
        }
    }

    companion object {
        fun addValue(line : String) {
            var name = line.substringBefore(":")
            var get  = line.substringAfter(": ")

            if (get.matches(Regex("-?\\d+"))) allValue[name] = Value(get.toLong())
            else {
                allValue[name] = get.split(" ").let { Value(value1 = it[0], operator = it[1][0], value2 = it[2]) }
            }

        }
    }
}

fun part1() : Long {
    return allValue[PRIMARY_MONKEY]!!.get()
}

fun part2() : Long {
    val first : Value = allValue[allValue[PRIMARY_MONKEY]!!.value1]!!
    val second : Value = allValue[allValue[PRIMARY_MONKEY]!!.value2]!!
    var vSecond = second.get()
    var cpt = 0L
    var by = 100_000_000_000L

    do{
        cpt += by
        allValue[HUMAN]!!.realValue = cpt

        var vFirst = first.get()

        if (vFirst < vSecond) {
            cpt -= by
            by /= 10L
        }

    }while(vFirst != vSecond)

    return cpt
}

fun main() {
    var content = readInput("d21/input")
    content.forEach { Value.addValue(it) }
    List<Int>(12){it}.sum()
    var v = 100_000_000_00
    var p1 = measureTimeMillis {
        for (o in 1..v) {
            0%5
        }
      //  println("Part 1 : " + part1())
    }

    println("Part 1 : {$p1}")

    p1 = measureTimeMillis {
        for(o in 1..v) {
            (Int.MAX_VALUE-1-1-1-1-1-1+1+1+1)%5
        }

    //println("Part 2 : " + part2())
    }

    println("Part 2 : {$p1}")
}