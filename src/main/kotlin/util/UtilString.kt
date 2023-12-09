package util
fun String.allInts(): MutableList<Int> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toInt() }.toMutableList()
}

fun String.allLong(): MutableList<Long> {
    return """-?\d+""".toRegex().findAll(this).map { it.value.toLong() }.toMutableList()
}


fun String.allDigits(): MutableList<Int> {
    return Regex("""\d""").listOfMatch(this).map { it.toInt() }.toMutableList()
}

fun Regex.listOfMatch(input: String): List<String> {
    return this.findAll(input).toMutableList().map { it.value }
}

fun String.findAllMatch(regex: String): List<String> {
    return Regex(regex).listOfMatch(this)
}

operator fun String.times(i: Int): String {
    var res = ""

    repeat(i) {
        res += this
    }

    return res
}

fun String.allIndexOf(string: String): MutableList<Int> {
    val res = mutableListOf<Int>()
    var index = this.indexOf(string)

    while (index != -1) {
        res.add(index)

        index = this.indexOf(string, index + 1)
    }

    return res;
}