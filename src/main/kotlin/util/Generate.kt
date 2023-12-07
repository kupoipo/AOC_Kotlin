package util
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeText

fun main() {
    val startDay = 20
    val endDay = 25
    val year = 2015
    try {
        Files.createDirectory(Paths.get("./src/main/kotlin/_$year"))
    } catch (_: Exception) {

    }

    for (i in startDay..endDay) {
        Files.createDirectory(Paths.get("./src/main/kotlin/_$year/d$i"))
        Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/test.txt"))
        Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/input.txt"))
        var f = Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/Day$i.kt"))

        var str = "package _$year.d$i\n" +
                "\n" +
                "import util.Day\n" +
                "import util.readFullText\n" +
                "import kotlin.system.measureNanoTime\n" +
                "class Day$i(override val input : String) : Day<Long>(input) {\n" +
                "    override fun solve1(): Long {\n" +
                "        return -1\n" +
                "    }\n" +
                "    override fun solve2(): Long {\n" +
                "        return -1\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "fun main() {\n" +
                "    val day = Day$i(readFullText(\"_$year/d$i/input\"))\n" +
                "\n" +
                "    val t1 = measureNanoTime { println(\"Part 1 : \" + day.solve1()) }\n" +
                "    println(\"Temps partie 1 : \${t1/1e9}s\")\n" +
                "\n" +
                "    val t2 = measureNanoTime { println(\"Part 2 : \" + day.solve2()) }\n" +
                "    println(\"Temps partie 2 : \${t2/1e9}s\")\n" +
                "" +
                "\n" +
                "    println()\n" +
                "    println()\n" +
                "\n" +
                "    val dayTest = Day$i(readFullText(\"_$year/d$i/test\"))\n" +
                "    val t1Test = measureNanoTime { println(\"TEST - Part 1 : \" + dayTest.solve1()) }\n" +
                "    println(\"Temps partie 1 : \${t1Test/1e9}s\")\n" +
                "\n" +
                "    val t2Test = measureNanoTime { println(\"TEST - Part 2 : \" + dayTest.solve2()) }\n" +
                "    println(\"Temps partie 2 : \${t2Test/1e9}s\")" +
                "\n" +
        "}"

        f.writeText(str)
    }

}