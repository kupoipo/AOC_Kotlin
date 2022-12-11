import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeLines
import kotlin.io.path.writeText

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()

fun generateDirectories() {
    for (i in 12..25) {
        Files.createDirectory(Paths.get("./src/main/kotlin/d$i"))
        Files.createFile(Paths.get("./src/main/kotlin/d$i/test.txt"))
        Files.createFile(Paths.get("./src/main/kotlin/d$i/input.txt"))
        var f = Files.createFile(Paths.get("./src/main/kotlin/d$i/Day$i.kt"))

        var str = "package d$i\n" +
                "import readInput\n\n" +
                "fun part1(content : List<String>) : String {\n" +
                "    \n" +
                "    return \"\"\n" +
                "}\n" +
                "\n" +
                "fun part2(content : List<String>) : String {\n" +
                "\n" +
                "    return \"\"\n" +
                "}\n" +
                "\n" +
                "fun main() {\n" +
                "    var content = readInput(\"d$i/input\")\n" +
                "    \n" +
                "    println(part1(content))\n" +
                "    println(part2(content))\n" +
                "}"

        f.writeText(str)
    }
}

fun main() {


}