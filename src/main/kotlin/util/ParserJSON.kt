package util

class JSONParser(private val jsonString: String) {

    fun parse(): JSON {
        val index = intArrayOf(0)
        return parseObject(index)
    }

    private fun parseObject(index: IntArray): JSON {
        val result = JSON()
        consumeWhitespace(index)
        if (jsonString[index[0]] == '{') {
            index[0]++
            consumeWhitespace(index)
            while (jsonString[index[0]] != '}') {
                val key = parseString(index)
                consumeWhitespace(index)
                if (jsonString[index[0]] == ':') {
                    index[0]++
                    consumeWhitespace(index)
                    val value = parseValue(index)
                    result.properties[key] = value!!
                    consumeWhitespace(index)
                    if (jsonString[index[0]] == ',') {
                        index[0]++
                        consumeWhitespace(index)
                    }
                } else {
                    throw JSONException("Expected ':' after key in object.")
                }
            }
            index[0]++
            return result
        } else {
            throw JSONException("Expected '{' to start an object.")
        }
    }

    private fun parseArray(index: IntArray): List<Any> {
        val result = mutableListOf<Any>()
        consumeWhitespace(index)
        if (jsonString[index[0]] == '[') {
            index[0]++
            consumeWhitespace(index)
            while (jsonString[index[0]] != ']') {
                val value = parseValue(index)
                result.add(value!!)
                consumeWhitespace(index)
                if (jsonString[index[0]] == ',') {
                    index[0]++
                    consumeWhitespace(index)
                }
            }
            index[0]++
            return result
        } else {
            throw JSONException("Expected '[' to start an array.")
        }
    }

    private fun parseValue(index: IntArray): Any? {
        return when (jsonString[index[0]]) {
            '{' -> parseObject(index)
            '[' -> parseArray(index)
            '"' -> parseString(index)
            't', 'f' -> parseBoolean(index)
            'n' -> parseNull(index)
            in '0'..'9', '-' -> parseNumber(index)
            else -> throw JSONException("Unexpected character: ${jsonString[index[0]]}")
        }
    }

    private fun parseString(index: IntArray): String {
        if (jsonString[index[0]] == '"') {
            index[0]++
            val stringBuilder = StringBuilder()
            while (jsonString[index[0]] != '"') {
                stringBuilder.append(jsonString[index[0]++])
            }
            index[0]++
            return stringBuilder.toString()
        } else {
            throw JSONException("Expected '\"' to start a string.")
        }
    }

    private fun parseBoolean(index: IntArray): Boolean {
        return when (jsonString[index[0]]) {
            't' -> {
                index[0] += 4
                true
            }
            'f' -> {
                index[0] += 5
                false
            }
            else -> throw JSONException("Invalid boolean value.")
        }
    }

    private fun parseNull(index: IntArray): Any? {
        if (jsonString.substring(index[0], index[0] + 4) == "null") {
            index[0] += 4
            return null
        } else {
            throw JSONException("Invalid null value.")
        }
    }

    private fun parseNumber(index: IntArray): Number {
        val start = index[0]
        while (jsonString[index[0]].isDigit() || jsonString[index[0]] == '.' || jsonString[index[0]] == '-') {
            index[0]++
        }
        val numberString = jsonString.substring(start, index[0])
        return if (numberString.contains('.')) {
            numberString.toDouble()
        } else {
            numberString.toLong()
        }
    }

    private fun consumeWhitespace(index: IntArray) {
        while (index[0] < jsonString.length && jsonString[index[0]].isWhitespace()) {
            index[0]++
        }
    }

    class JSONException(message: String) : Exception(message)
}

fun main() {
    val jsonString = """{"name": "John Doe", "age": 30, "city": "New York", "isStudent": false, "grades": [90, 85, 95]}"""

    val parser = JSONParser(jsonString)
    val jsonObject = parser.parse()

    println(jsonObject)
}