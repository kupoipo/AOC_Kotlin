package util

import java.io.File
import kotlin.math.pow


fun charToInt(c: Any): Int {
    return if (c is Char) c.digitToInt() else 0
}

fun readFullText(name: String) = File("src/main/kotlin", "$name.txt")
    .readText().replace("\r", "")

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()

fun <T> List<T>.allArrangement(minElement: Int = -1, maxElement: Int = -1) : List<List<T>> {
    val res = mutableListOf<List<T>>()

    for (binaryString in 2.0.pow(this.size).toInt().allBinaryPossibility()) {
        this.filterIndexed { index, _ -> binaryString[index] == '1' }.also {
            val nbOne = binaryString.count { it == '1' }

            if (maxElement == -1 && minElement == -1) {
                res.add(it)
            } else {
                if (maxElement == -1) {
                    if (minElement <= nbOne) {
                        res.add(it)
                    }
                } else if (minElement == -1) {
                    if (maxElement >= nbOne) {
                        res.add(it)
                    }
                } else {
                    if (nbOne in minElement..maxElement) {
                        res.add(it)
                    }
                }
            }
        }
    }

    return res.sortedBy { it.size }
}

fun random(max: Int, min: Int = 0): Int {
    return (Math.random() * (max - min) + min).toInt()
}