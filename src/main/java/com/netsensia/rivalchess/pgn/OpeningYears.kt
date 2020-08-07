package com.netsensia.rivalchess.pgn

import java.io.File

fun main(args: Array<String>) {
    val yearMap = mutableMapOf<Int,MutableMap<String,Int>>()
    val regex = """Date "([0-9][0-9][0-9][0-9])""".toRegex()
    File("pgn/openings").walk().forEach { file ->
        if (file.isFile) {
            file.forEachLine { line ->
                val matchResult = regex.find(line)
                if (matchResult != null) {
                    val year = matchResult.groups[1]!!.value.toInt()
                    val openingMap = if (yearMap.containsKey(year)) {
                        yearMap[year]!!
                    } else {
                        mutableMapOf()
                    }
                    if (openingMap.containsKey(file.name)) {
                        openingMap[file.name] = openingMap[file.name]!! + 1
                    } else {
                        openingMap[file.name] = 1
                    }
                    yearMap[year] = openingMap
                }
            }
        }
    }

    yearMap.toSortedMap().forEach { entry ->
        println("${entry.key}")
        entry.value.forEach { opening ->
            println("${opening.key} -> ${opening.value}")
        }
    }
}
