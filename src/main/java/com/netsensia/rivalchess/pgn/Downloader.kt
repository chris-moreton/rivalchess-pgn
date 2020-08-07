package com.netsensia.rivalchess.pgn

import java.io.File
import java.net.URL

fun main(args: Array<String>) {
    val r = """href="(openings/.*?.pgn)""".toRegex()

    File("input/source.html").forEachLine { line ->
        val matchResult = r.find(line)
        if (matchResult != null) {
            val filename = matchResult.groups[1]!!.value
            println(filename)
            val url = URL("https://www.pgnmentor.com/" + filename)
            val bytes = url.readText()
            File("output/" + filename).writeText(bytes)
        }
    }
}