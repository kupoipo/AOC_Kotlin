package d01

import Day

class Day01 : Day() {
    override fun solve1(content: String): String = content.split("\n\n").map { it.split("\n").map { it.toInt() }.sum() }.sorted().take(1).toString()

    override fun solve2(content: String): String {
        TODO("Not yet implemented")
    }
}