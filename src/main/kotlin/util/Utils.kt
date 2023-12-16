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

fun <T> getPermutations(list: List<T>): List<List<T>> {
    if (list.size <= 1) {
        return listOf(list)
    }

    val result = mutableListOf<List<T>>()

    for (i in list.indices) {
        val element = list[i]
        val remainingList = list.subList(0, i) + list.subList(i + 1, list.size)
        val permutationsOfRemainingList = getPermutations(remainingList)

        for (permutation in permutationsOfRemainingList) {
            result.add(listOf(element) + permutation)
        }
    }

    return result
}

fun <T> List<T>.allPermutation() : List<List<T>> {
    return getPermutations(this)
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

fun md5(string: String) : String {
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