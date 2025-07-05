package util

import kotlin.math.pow

fun Int.toPrimeFactor(): Map<Long, Int> = this.toLong().toPrimeFactor()

fun Long.toPrimeFactor(): Map<Long, Int> {
    val factors = mutableListOf<Long>()
    var divider = 2L
    var nb = this

    while (nb > 1) {
        while (nb % divider == 0L) {
            factors.add(divider)
            nb /= divider
        }
        divider += 1
    }

    return factors.toSet().associateWith { prime -> factors.count { it == prime } }
}

fun Double.hasDecimalPart() : Boolean {
    return this - this.toLong() > 0.0000001
}

fun Int.allBinaryPossibility() : List<String> {
    val pad = Integer.toBinaryString(this).length - 1

    return buildList {
        (0 until this@allBinaryPossibility).forEach {
            this.add(Integer.toBinaryString(it).padStart(pad, '0'))
        }
    }
}
fun PPCM(nb: List<Long>): Long {
    val m = mutableMapOf<Long, Int>()

    nb.map {
        it.toPrimeFactor().forEach { (prime, factor) ->
            m[prime] = m[prime]?.coerceAtMost(factor) ?: factor
        }
    }

    return m.map { (k, v) -> k.toDouble().pow(v) }.reduce { i, j -> i * j }.toLong()
}