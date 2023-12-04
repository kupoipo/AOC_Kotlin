package util

import java.lang.RuntimeException

class JSON {
    val properties = mutableMapOf<String, Any>()
    operator fun get(key: String): Any? = properties[key]
    fun getJSON(key: String): JSON {
        return properties[key] as JSON
    }

    operator fun iterator(): MutableIterator<MutableMap.MutableEntry<String, Any>> {
        return properties.iterator()
    }

    fun getList(key: String): List<Any> {
        return properties[key] as List<Any>
    }
}
