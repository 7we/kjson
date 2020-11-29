package xyz.monology.kjson

inline class JsonBuilder(val stringBuilder: StringBuilder) {
    inline operator fun String.minus(any: Any?) {
        if (stringBuilder.length != 1) {
            stringBuilder.append(", ")
        }
        stringBuilder.append("\"$this\": ${any.toJsonString()}")
    }

    inline operator fun String.get(vararg values: Any){
        this - values
    }

    inline operator fun String.invoke(builder: JsonBuilder.() -> Unit) {
        this - JsonBuilder(StringBuilder("{")).apply(builder)
    }

    override inline fun toString(): String {
        return stringBuilder.append("}").toString()
    }
}

fun Any?.toJsonString(): String {
    return when (this) {
        is JsonBuilder -> toString()
        is String -> "\"$this\""
        is Iterable<*> -> "[" + this.joinToString { it.toJsonString() } + "]"
        is Array<*> -> "[" + this.joinToString { it.toJsonString() } + "]"
        is Boolean -> toString()
        is Byte -> toString()
        is Short -> toString()
        is Int -> toString()
        is Long -> toString()
        is Float -> toString()
        is Double -> toString()
        else -> "\"" + toString() + "\""
    }
}

inline fun json(builder: JsonBuilder.() -> Unit): JsonBuilder {
    return JsonBuilder(StringBuilder("{")).apply(builder)
}