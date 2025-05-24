package ir.miare.androidcodechallenge.utils


inline fun <reified T> isListOfType(list: List<*>): Boolean {
    return list.all { it is T }
}
