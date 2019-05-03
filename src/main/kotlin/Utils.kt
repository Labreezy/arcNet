package classes


fun writeToFile(fileName: String, text: String) {
    println("write \"${text}\" to ${fileName}.txt")
    // TODO: soon
}

fun truncate(name: String, length: Int): String {
    val re = Regex("[^A-Za-z0-9 ]")
    if (name.length > length) return re.replace(name, "X").substring(0, length)
    else return re.replace(name, "X")
}

fun addCommas(inStr:String):String {
    var commas = if (inStr.length % 3 == 0) (inStr.length/3)-1 else inStr.length/3
    var outStr = ""
    for (i in 0..commas-1) outStr = if (inStr.length > 3) ",${inStr.substring(inStr.length-(3*(i+1)), inStr.length-(3*i))}${outStr}" else "${inStr.substring(inStr.length-(3*(i+1)), inStr.length-(3*i))}${outStr}"
    return inStr.substring(0, inStr.length-(3*commas)) + outStr
}

object Character {
    const val SO: Byte = 0x00 ;const val KY: Byte = 0x01 ;const val MA: Byte = 0x02 ;const val MI: Byte = 0x03 ;const val ZA: Byte = 0x04 ;const val PO: Byte = 0x05 ;const val CH: Byte = 0x06 ;const val FA: Byte = 0x07 ;const val AX: Byte = 0x08 ;const val VE: Byte = 0x09 ;const val SL: Byte = 0x0A ;const val IN: Byte = 0x0B ;const val BE: Byte = 0x0C ;const val RA: Byte = 0x0D ;const val SI: Byte = 0x0E ;const val EL: Byte = 0x0F ;const val LE: Byte = 0x10 ;const val JO: Byte = 0x11 ;const val JC: Byte = 0x12 ;const val JM: Byte = 0x13 ;const val KU: Byte = 0x14 ;const val RV: Byte = 0x15 ;const val DI: Byte = 0x16 ;const val BA: Byte = 0x17 ;const val AN: Byte = 0x18
    fun getCharacterName(byte: Byte, shortened: Boolean): String {
        when (byte) {
            SO -> if (shortened) return "SO" else return "Sol Badguy"
            KY -> if (shortened) return "KY" else return "Ky Kiske"
            MA -> if (shortened) return "MA" else return "May"
            MI -> if (shortened) return "MI" else return "Millia Rage"
            ZA -> if (shortened) return "ZA" else return "Zato=1"
            PO -> if (shortened) return "PO" else return "Potemkin"
            CH -> if (shortened) return "CH" else return "Chipp Zanuff"
            FA -> if (shortened) return "FA" else return "Faust"
            AX -> if (shortened) return "AX" else return "Axl Low"
            VE -> if (shortened) return "VE" else return "Venom"
            SL -> if (shortened) return "SL" else return "Slayer"
            IN -> if (shortened) return "IN" else return "I-No"
            BE -> if (shortened) return "BE" else return "Bedman"
            RA -> if (shortened) return "RA" else return "Ramlethal Valentine"
            SI -> if (shortened) return "SI" else return "Sin Kiske"
            EL -> if (shortened) return "EL" else return "Elpelt Valentine"
            LE -> if (shortened) return "LE" else return "Leo Whitefang"
            JO -> if (shortened) return "JO" else return "Johnny Sfondi"
            JC -> if (shortened) return "JC" else return "Jack-O Valentine"
            JM -> if (shortened) return "JM" else return "Jam Kuradoberi"
            KU -> if (shortened) return "KU" else return "Kum Haehyun"
            RV -> if (shortened) return "RV" else return "Raven"
            DI -> if (shortened) return "DI" else return "Dizzy"
            BA -> if (shortened) return "BA" else return "Baiken"
            AN -> if (shortened) return "AN" else return "Answer"
            else -> if (shortened) return "??" else return "???"
        }
    }
}