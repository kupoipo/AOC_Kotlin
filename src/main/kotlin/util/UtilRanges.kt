package util

import java.lang.RuntimeException
import kotlin.math.max
import kotlin.math.min

fun LongRange.contains(other: LongRange) : Boolean = this.first <= other.first && this.last >= other.last
fun LongRange.overlap(other: LongRange) : Boolean {
    if (this.contains(other))
        return true

    return min(this.last, other.last) >= max(this.first, other.first)
}

fun LongRange.getOverlapped(other: LongRange) : LongRange {
    if (!this.overlap(other))
        throw RuntimeException("Ranges $this and $other doesn't overlap each other")

    return max(this.first, other.first).. min(this.last, other.last)
}