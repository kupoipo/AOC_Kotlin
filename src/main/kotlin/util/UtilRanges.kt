package util

import java.lang.RuntimeException
import kotlin.math.max
import kotlin.math.min

fun LongRange.contains(other: LongRange) : Boolean = this.first <= other.first && this.last >= other.last
fun LongRange.isOverlapping(other: LongRange) : Boolean {
    if (this.contains(other))
        return true

    return min(this.last, other.last) >= max(this.first, other.first)
}

fun LongRange.offCut(other: LongRange) : LongRange{
    if (!this.isOverlapping(other))
        throw RuntimeException("No offcut existing for $this and $other.")

    return if (this.last == other.last) this.first..other.first
    else other.last..this.last

}

fun LongRange.overlap(other: LongRange) : LongRange {
    if (!this.isOverlapping(other))
        throw RuntimeException("Ranges $this and $other doesn't overlap each other")

    if (this.contains(other)) return other
    if (other.contains(this)) return this

    return max(this.first, other.first).. min(this.last, other.last)
}


infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

