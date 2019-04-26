package classes

class Legacy(legacyStr:String) {
    val steamId = Regex("""(?<=ID)(\d+)""").find(legacyStr)!!.value.toLongOrNull() ?: -1
    var bountyWon = Regex("""(?<=Bw)(\d+)""").find(legacyStr)!!.value.toLongOrNull() ?: 0
    var bountyTotal = Regex("""(?<=Bt)(\d+)""").find(legacyStr)!!.value.toLongOrNull() ?: 0
    var matchesWon = Regex("""(?<=Mw)(\d+)""").find(legacyStr)!!.value.toLongOrNull() ?: 0
    var matchesTotal = Regex("""(?<=Mt)(\d+)""").find(legacyStr)!!.value.toLongOrNull() ?: 0
    fun getDataStr():String = "ID${steamId}Bw${bountyWon}Bt${bountyTotal}Mw${matchesWon}Mt${matchesTotal}!"
}