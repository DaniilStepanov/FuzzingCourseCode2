package org.itmo.fuzzing.task2

class CanonicalSolution {

    companion object {
        fun canonicalSimplifyPath(s: String): String {
            try {
                val commands = s.split("/").map { it.trim() }.filter { it.isNotEmpty() }
                val result = mutableListOf<String>()
                for (c in commands) {
                    when (c) {
                        ".." -> result.removeLastOrNull() ?: result.add("/")
                        "." -> {}
                        else -> result.add(c)
                    }
                }
                return if (result.first() == "/") {
                    result.first() + result.takeLast(result.size - 1).joinToString("/")
                } else {
                    "/" + result.joinToString("/")
                }
            } catch (e: Exception) {
                return "/"
            }
        }
    }
}