package signature

import java.io.File

const val FIRST_LINE = 1 //line 2 -- zero based
const val SECOND_LINE = 2 //line 3 -- zero based
const val UPPER_TO_TWENTYSIX = 39
const val LOWER_TO_ZERO = 97
const val NEXT_ROMAN_CHAR = 11
const val NEXT_MEDIUM_CHAR = 4
const val ROMAN_WHITESPACE = 10
const val MEDIUM_WHITESPACE = 5

fun main() {
    print("Write your name: ")
    val name = readln()
    print("Write your card alias: ")
    val status = readln()

    val romanFont = File("C:/Users/Homero/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/roman.txt").readLines()
    val mediumFont = File("C:/Users/Homero/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/medium.txt").readLines()

    //Get roman character width
    var nameWidth = 0
    name.forEach {
        nameWidth += if (it == ' ') {
            ROMAN_WHITESPACE
        } else {
            val index = if (it.isUpperCase()) {
                FIRST_LINE + (it.toInt() - UPPER_TO_TWENTYSIX) * NEXT_ROMAN_CHAR //NO
            } else {
                FIRST_LINE + (it.toInt() - LOWER_TO_ZERO) * NEXT_ROMAN_CHAR //OK
            }
            romanFont[index].substringAfter(' ').toInt()
        }
    }

    //Get medium character width
    var statusWidth = 0
    status.forEach {
        statusWidth += if (it == ' ') {
            MEDIUM_WHITESPACE
        } else {
            val index = if (it.isUpperCase()) {
                FIRST_LINE + (it.toInt() - UPPER_TO_TWENTYSIX) * NEXT_MEDIUM_CHAR
            } else {
                FIRST_LINE + (it.toInt() - LOWER_TO_ZERO) * NEXT_MEDIUM_CHAR
            }
            mediumFont[index].substringAfter(' ').toInt()
        }
    }

    //Get booleans
    val nameIsBigger = nameWidth > statusWidth
    val evenName = nameWidth % 2 == 0
    val evenStatus = statusWidth % 2 == 0
    val unevenTag = evenName xor evenStatus
    
    //Get tag length & margins
    val tagLength = if (nameIsBigger) nameWidth + 8 else statusWidth + 8
    var nameMarginLeft = 0
    var nameMarginRight = 0
    var statusMarginLeft = 0
    var statusMarginRight = 0

    //Check even or uneven tag
    if (unevenTag) {
        if(nameIsBigger) {
            nameMarginLeft = 2
            nameMarginRight = 2
            statusMarginLeft = (nameWidth - statusWidth) / 2 + 2
            statusMarginRight = statusMarginLeft + 1
        } else {
            nameMarginLeft = (statusWidth - nameWidth) / 2 + 2
            nameMarginRight = nameMarginLeft + 1
            statusMarginLeft = 2
            statusMarginRight = statusMarginLeft
        }
    } else {
        if (nameIsBigger) {
            nameMarginLeft = 2
            nameMarginRight = nameMarginLeft
            statusMarginLeft = (nameWidth - statusWidth) / 2 + 2
            statusMarginRight = statusMarginLeft
        } else {
            nameMarginLeft = (statusWidth - nameWidth) / 2 + 2
            nameMarginRight = nameMarginLeft
            statusMarginLeft = 2
            statusMarginRight = statusMarginLeft
        }
    }

    //Print top
    repeat (tagLength) { print('8') }
    println()
        
    //Print 10 lines of name
    val emptyLine = "88" + " ".repeat(tagLength - 4) + "88"

    for(line in 0..9) {
        var lineToPrint = ""

        //Left margin
        lineToPrint += "88"
        repeat (nameMarginLeft) { lineToPrint += ' ' }

        //Name line
        name.forEach {
            if (it == ' ') {
                repeat(ROMAN_WHITESPACE) { lineToPrint += ' ' }
            } else {
                val index = if (it.isUpperCase()) {
                    SECOND_LINE + (it.toInt() - UPPER_TO_TWENTYSIX) * NEXT_ROMAN_CHAR
                } else {
                    SECOND_LINE + (it.toInt() - LOWER_TO_ZERO) * NEXT_ROMAN_CHAR
                }
                lineToPrint += romanFont[index + line]
            }
        }

        //Right margin
        repeat (nameMarginRight) { lineToPrint += ' ' }
        lineToPrint += "88"

        //Check and print line
        println(lineToPrint)
    }

    //Print 3 lines of status
    for(line in 0..2) {

        //Left margin
        print("88")
        repeat (statusMarginLeft) { print(' ') }

        //Name line
        status.forEach {
            if (it == ' ') {
                repeat(MEDIUM_WHITESPACE) { print(' ') }
            } else {
                val index = if (it.isUpperCase()) {
                    SECOND_LINE + (it.toInt() - UPPER_TO_TWENTYSIX) * NEXT_MEDIUM_CHAR
                } else {
                    SECOND_LINE + (it.toInt() - LOWER_TO_ZERO) * NEXT_MEDIUM_CHAR
                }
                print(mediumFont[index + line])
            }
        }

        //Right margin
        repeat (statusMarginRight) { print(' ') }
        println("88")
    }

    //Print bottom
    repeat (tagLength) { print('8') }
    println()

}
