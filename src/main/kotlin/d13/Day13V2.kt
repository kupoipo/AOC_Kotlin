package d13

sealed interface PackedValue : Comparable<PackedValue>
class SingleValue(val value : Int) : PackedValue {
    override fun compareTo(other: PackedValue): Int = when (other) {
        is SingleValue -> this.compareTo(other)
        is MultipleValue -> MultipleValue(mutableListOf(this)) compareTo other
    }
}

class MultipleValue(val value : List<PackedValue>) : PackedValue {
    override fun compareTo(other: PackedValue): Int = when (other) {
        is SingleValue -> this compareTo MultipleValue(mutableListOf(other))
        is MultipleValue -> {
            var firstIte = this.value.iterator()
            var otherIte = this.value.iterator()

            while (firstIte.hasNext() && otherIte.hasNext()) {
                val res = firstIte.next().compareTo(otherIte.next())
                if (res != 0)
                    res
            }

            // Si on a fini la deuxième liste avant la première
            if (otherIte.hasNext()) 1

            // On a fini la première liste avant la deuxième
            if (firstIte.hasNext()) -1

            0
        }
    }
}
/*
fun part1V2(content : List<String>) : String {
    var allLine = mutableListOf<PackedValue?>()

    for (i in content.indices step 3) {
        allLine.add(parseLineV2(content[i]))
        allLine.add(parseLineV2(content[i+1]))
    }

    return ""
}
*/
fun main() {



}