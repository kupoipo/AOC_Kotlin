package util

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.pow


fun charToInt(c: Any): Int {
    return if (c is Char) c.digitToInt() else 0
}

fun readFullText(name: String) = File("src/main/kotlin", "$name.txt")
    .readText().replace("\r", "")

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()

fun PGCD(a: Int, b: Int): Int {
    var x = a
    var y = b

    while (y != 0) {
        val temp = y
        y = x % y
        x = temp
    }
    return x
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return (a * b) / gcd(a, b)
}

fun lcmOf(vararg numbers: Long): Long {
    return numbers.reduce { acc, num -> lcm(acc, num) }
}

fun lcmOf(numbers: List<Long>): Long {
    return numbers.reduce { acc, num -> lcm(acc, num) }
}


fun <T> permutations(list: List<T>): List<List<T>> {
    if (list.size <= 1) {
        return listOf(list)
    }

    val result = mutableListOf<List<T>>()

    for (i in list.indices) {
        val element = list[i]
        val remainingList = list.subList(0, i) + list.subList(i + 1, list.size)
        val permutationsOfRemainingList = permutations(remainingList)

        for (permutation in permutationsOfRemainingList) {
            result.add(listOf(element) + permutation)
        }
    }

    return result
}

/**
 * If withReverse, will give [0,0], [0,1] [1,0] [1,1], without will give [0,0] [0,1] [1,1] for pairs in [0..1]
 */
fun <E> List<E>.pairs(withReverse: Boolean = false): MutableList<Pair<E, E>> {
    val res = mutableListOf<Pair<E, E>>()

    for (i in 0..this.lastIndex) {
        for (j in (if (withReverse) 0 else i + 1)..this.lastIndex) {
            if (i == j) continue
            res.add(this@pairs[i] to this@pairs[j])
        }
    }

    return res
}

fun <T> List<T>.permutation(): List<List<T>> {
    return permutations(this)
}

fun <T> combination(from: List<T>, current: List<T>, nbPermutation: Int): List<List<T>> {
    if (current.size >= nbPermutation) return listOf(current)
    val res = mutableListOf<List<T>>()

    for (index in from.indices) {
        res.addAll(combination(from.drop(index + 1), current.toMutableList().apply { add(from[index]) }, nbPermutation))
    }

    return res
}

fun <T> List<T>.combination(nbPermutation: Int = 2): List<List<T>> {
    return combination(this, listOf(), nbPermutation)
}

fun <T> List<T>.allArrangement(
    minElement: Int = -1,
    maxElement: Int = -1,
    function: (List<T>) -> (Boolean) = { true }
): List<List<T>> {
    val res = mutableListOf<List<T>>()

    for (binaryString in 2.0.pow(this.size).toInt().allBinaryPossibility()) {
        this.filterIndexed { index, _ -> binaryString[index] == '1' }.also {
            val nbOne = binaryString.count { it == '1' }
            var tryToAdd = false

            if (maxElement == -1 && minElement == -1) {
                tryToAdd = true
            } else {
                if (maxElement == -1) {
                    if (minElement <= nbOne) {
                        tryToAdd = true
                    }
                } else if (minElement == -1) {
                    if (maxElement >= nbOne) {
                        tryToAdd = true
                    }
                } else {
                    if (nbOne in minElement..maxElement) {
                        tryToAdd = true
                    }
                }
            }

            if (tryToAdd && function(it)) {
                res.add(it)
            }
        }
    }

    return res.sortedBy { it.size }
}

fun md5(string: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(string.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}

fun random(max: Int, min: Int = 0): Int {
    return (Math.random() * (max - min) + min).toInt()
}

operator fun <E> MutableList<E>.times(t: Int): MutableList<E> {
    val res = mutableListOf<E>()

    repeat(t) {
        for (e in this) {
            res.add(e)
        }
    }

    return res
}

fun Long.isPrime(): Boolean {
    if (this <= 1) {
        return false
    }
    if (this <= 3) {
        return true
    }
    if (this % 2 == 0L || this % 3 == 0L) {
        return false
    }
    var i = 5L
    while (i * i <= this) {
        if (this % i == 0L || this % (i + 2) == 0L) {
            return false
        }
        i += 6
    }
    return true
}