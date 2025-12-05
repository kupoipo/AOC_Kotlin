package util

import java.lang.RuntimeException
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last
fun LongRange.contains(other: LongRange): Boolean = this.first <= other.first && this.last >= other.last
fun LongRange.isOverlapping(other: LongRange): Boolean {
    if (this.contains(other))
        return true

    return min(this.last, other.last) >= max(this.first, other.first)
}

fun LongRange.merge(other: LongRange): LongRange {
    return min(this.first, other.first)..max(this.last, other.last)
}

fun LongRange.size(): Long = last - first + 1

fun LongRange.offCut(other: LongRange): LongRange {
    if (!this.isOverlapping(other))
        throw RuntimeException("No offcut existing for $this and $other.")

    return if (this.last == other.last) this.first..other.first
    else other.last..this.last


}

fun LongRange.minus(other: LongRange): List<LongRange> {
    val overlap = overlap(other) ?: return listOf(this)
    val result = mutableListOf<LongRange>()

    if (start < overlap.first) result.add(LongRange(start, overlap.first - 1))
    if (overlap.last < last) result.add(LongRange(overlap.last + 1, last))

    return result
}

fun LongRange.overlap(other: LongRange): LongRange {
    if (!this.isOverlapping(other))
        throw RuntimeException("Ranges $this and $other doesn't overlap each other")

    if (this.contains(other)) return other
    if (other.contains(this)) return this

    return max(this.first, other.first)..min(this.last, other.last)
}

fun <T> LinkedList<T>.circle(count: Int) {
    if (count < 0) {
        repeat(abs(count)) {
            val el = this.removeFirst()
            this.add(el)
        }
    } else {
        repeat(count) {
            val el = this.removeLast()
            this.add(0, el)
        }
    }
}

infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

