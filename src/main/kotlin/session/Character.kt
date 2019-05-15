package session

import javafx.geometry.Rectangle2D

object Character {

    const val SO: Byte = 0x00 // 0
    const val KY: Byte = 0x01 // 1
    const val MA: Byte = 0x02 // 2
    const val MI: Byte = 0x03 // 3
    const val ZA: Byte = 0x04 // 4
    const val PO: Byte = 0x05 // 5
    const val CH: Byte = 0x06 // 6
    const val FA: Byte = 0x07 // 7
    const val AX: Byte = 0x08 // 8
    const val VE: Byte = 0x09 // 9
    const val SL: Byte = 0x0A // 10
    const val IN: Byte = 0x0B // 11
    const val BE: Byte = 0x0C // 12
    const val RA: Byte = 0x0D // 13
    const val SI: Byte = 0x0E // 14
    const val EL: Byte = 0x0F // 15
    const val LE: Byte = 0x10 // 16
    const val JO: Byte = 0x11 // 17
    const val JC: Byte = 0x12 // 18
    const val JM: Byte = 0x13 // 19
    const val KU: Byte = 0x14 // 20
    const val RV: Byte = 0x15 // 21
    const val DI: Byte = 0x16 // 22
    const val BA: Byte = 0x17 // 23
    const val AN: Byte = 0x18 // 24

    fun getCharacterInitials(integer: Int) = getCharacterInitials(integer.toByte())
    fun getCharacterInitials(byte: Byte): String {
        when (byte) {
            SO -> return "SO"
            KY -> return "KY"
            MA -> return "MA"
            MI -> return "MI"
            ZA -> return "ZA"
            PO -> return "PO"
            CH -> return "CH"
            FA -> return "FA"
            AX -> return "AX"
            VE -> return "VE"
            SL -> return "SL"
            IN -> return "IN"
            BE -> return "BE"
            RA -> return "RA"
            SI -> return "SI"
            EL -> return "EL"
            LE -> return "LE"
            JO -> return "JO"
            JC -> return "JC"
            JM -> return "JM"
            KU -> return "KU"
            RV -> return "RV"
            DI -> return "DI"
            BA -> return "BA"
            AN -> return "AN"
            else -> return "??"
        }
    }

    fun getCharacterName(integer: Int) = getCharacterName(integer.toByte())
    fun getCharacterName(byte: Byte): String {
        when (byte) {
            SO -> return "Sol Badguy"
            KY -> return "Ky Kiske"
            MA -> return "May"
            MI -> return "Millia Rage"
            ZA -> return "Zato=1"
            PO -> return "Potemkin"
            CH -> return "Chipp Zanuff"
            FA -> return "Faust"
            AX -> return "Axl Low"
            VE -> return "Venom"
            SL -> return "Slayer"
            IN -> return "I-No"
            BE -> return "Bedman"
            RA -> return "Ramlethal Valentine"
            SI -> return "Sin Kiske"
            EL -> return "Elpelt Valentine"
            LE -> return "Leo Whitefang"
            JO -> return "Johnny Sfondi"
            JC -> return "Jack-O Valentine"
            JM -> return "Jam Kuradoberi"
            KU -> return "Kum Haehyun"
            RV -> return "Raven"
            DI -> return "Dizzy"
            BA -> return "Baiken"
            AN -> return "Answer"
            else -> return "???"
        }
    }

    fun getCharacterPortrait(integer: Int) = getCharacterPortrait(integer.toByte())
    fun getCharacterPortrait(byte: Byte = -0x1, idle: Boolean = false): Rectangle2D {
        val offsetX = if (idle) 512 else 0
        when (byte) {
            SO -> return Rectangle2D(offsetX + 0.0, 0.0, 64.0, 64.0)
            KY -> return Rectangle2D(offsetX + 64.0, 0.0, 64.0, 64.0)
            MA -> return Rectangle2D(offsetX + 128.0, 0.0, 64.0, 64.0)
            MI -> return Rectangle2D(offsetX + 192.0, 0.0, 64.0, 64.0)
            ZA -> return Rectangle2D(offsetX + 256.0, 0.0, 64.0, 64.0)
            PO -> return Rectangle2D(offsetX + 320.0, 0.0, 64.0, 64.0)
            CH -> return Rectangle2D(offsetX + 384.0, 0.0, 64.0, 64.0)
            FA -> return Rectangle2D(offsetX + 448.0, 0.0, 64.0, 64.0)
            AX -> return Rectangle2D(offsetX + 0.0, 64.0, 64.0, 64.0)
            VE -> return Rectangle2D(offsetX + 64.0, 64.0, 64.0, 64.0)
            SL -> return Rectangle2D(offsetX + 128.0, 64.0, 64.0, 64.0)
            IN -> return Rectangle2D(offsetX + 192.0, 64.0, 64.0, 64.0)
            BE -> return Rectangle2D(offsetX + 256.0, 64.0, 64.0, 64.0)
            RA -> return Rectangle2D(offsetX + 320.0, 64.0, 64.0, 64.0)
            SI -> return Rectangle2D(offsetX + 384.0, 64.0, 64.0, 64.0)
            EL -> return Rectangle2D(offsetX + 448.0, 64.0, 64.0, 64.0)
            LE -> return Rectangle2D(offsetX + 0.0, 128.0, 64.0, 64.0)
            JO -> return Rectangle2D(offsetX + 64.0, 128.0, 64.0, 64.0)
            JC -> return Rectangle2D(offsetX + 128.0, 128.0, 64.0, 64.0)
            JM -> return Rectangle2D(offsetX + 192.0, 128.0, 64.0, 64.0)
            KU -> return Rectangle2D(offsetX + 256.0, 128.0, 64.0, 64.0)
            RV -> return Rectangle2D(offsetX + 320.0, 128.0, 64.0, 64.0)
            DI -> return Rectangle2D(offsetX + 384.0, 128.0, 64.0, 64.0)
            BA -> return Rectangle2D(offsetX + 448.0, 128.0, 64.0, 64.0)
            AN -> return Rectangle2D(offsetX + 0.0, 192.0, 64.0, 64.0)
            else -> return Rectangle2D(offsetX + 64.0, 192.0, 64.0, 64.0)
        }
    }

}