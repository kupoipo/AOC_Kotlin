package util

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

fun Int.allBinaryPossibility() : List<String> {
    val pad = Integer.toBinaryString(this).length - 1

    return buildList {
        (0..this@allBinaryPossibility).forEach {
            this.add(Integer.toBinaryString(it).padStart(pad, '0'))
        }
    }
}