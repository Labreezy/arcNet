package streamlabs

import session.Player
import utils.writeToFile


class SlobsHandler {

    var showhud = true
    var forcehud = false

    fun writeLobbyFiles(players: List<Player>) {
        for (i in 0..3) if (showhud && players.size > i && players.get(i).isScoreboardWorthy()) {
            val remote = "${getPlaceString(i+1)}\n" +
                    "Backdrop:\n" +
                    "${getFullscreen(true)}\n" +
                    "Name:\n" +
                    "${players.get(i).getNameString()}\n" +
                    "Bounty:\n" +
                    "${players.get(i).getBountyFormatted(0f)}\n" +
                    "Rating:\n" +
                    "${players.get(i).getRatingLetter()}\n" +
                    "Chain:\n" +
                    (if (players.get(i).getChain()>0) players.get(i).getChain() else " ") + "\n" +
                    "Change:\n" +
                    "${players.get(i).getChangeString(0f)}\n" +
                    "Character:\n" +
                    "${players.get(i).getCharacter()}\n" +
                    ""
            writeToFile("remote${i}", remote)
        } else writeToFile("remote${i}", "")
    }

    private fun getFullscreen(enabled: Boolean): String {
        if (enabled) return "████████████████████████████████████████████████████████████████████████████████████████████████"
        else return "                                                                                                "
    }

    private fun getPlaceString(i: Int):String {
        when(i.toString().substring(i.toString().length-1,i.toString().length)) {
            "1" -> return "1st"
            "2" -> return "2nd"
            "3" -> return "3rd"
            else -> return "${i}th"
        }
    }
}

