package application

import glm_.vec2.Vec2
import imgui.*
import objects.Col4
import objects.Col4.statColor
import session.Player
import imgui.ImGui as Ui

object Playerviews {
    private val CELL_FLAGS = WindowFlag.NoTitleBar or WindowFlag.NoCollapse or WindowFlag.NoScrollbar or WindowFlag.NoResize or WindowFlag.NoSavedSettings

    fun generatePlayerView(player: Player, height: Float) {
        Ui.setNextWindowPos(Vec2(Ui.io.displaySize[0].toFloat() - 550f, (70f * height) + 40f), Cond.Always, Vec2(0f))
        Ui.setNextWindowSize(Vec2(550f, 70f))
        Ui.setNextWindowBgAlpha(0.8f)
        Ui.pushStyleVar(StyleVar.WindowRounding, 0f)
        functionalProgramming.withWindow("title${height}", null, CELL_FLAGS) {
            // Portrait
            Ui.image(2, Vec2(40,52))
            Ui.sameLine(60)
            Ui.beginGroup()

            // ------------------ TOP ROW ------------------
            // Player
            Ui.textColored(Col4.name(player.present), player.getNameString())
            // Rating
            Ui.sameLine(210)
            Ui.textColored(statColor(player, (player.getRating()*10).toInt(), Col4.GRY_L), "Rating:")
            Ui.sameLine(270)
            Ui.textColored(player.getRatingColor(), player.getRatingLetter())
            // Status
            Ui.sameLine(320)
            Ui.pushItemWidth(Ui.calcItemWidth()/3)
            Ui.progressBar(getLoadBarValue(player),  Vec2(160, 16), getLoadStatusString(player))

            // ------------------ MID ROW ------------------
            // Character
            Ui.textColored(if (player.present) Col4.BLU_M else Col4.BLU_D, player.getCharacter(false))
            // Chains
            Ui.sameLine(210)
            Ui.textColored(statColor(player, player.getChain(), Col4.GRY_L), "Chains:")
            Ui.sameLine(270)
            Ui.textColored(player.getChainColor(), player.getChainString())
            // Record
            Ui.sameLine(325)
            Ui.textColored(statColor(player, player.getMatchesWon(), Col4.GRY_L), player.getMatchesWonString())
            Ui.sameLine(405)
            Ui.textColored(statColor(player, player.getMatchesPlayed(), Col4.GRY_L), player.getMatchesPlayedString())

            // ------------------ BOT ROW ------------------
            // Bounty
            Ui.textColored(statColor(player, player.getBounty(), Col4.GOLD), player.getBountyString(player.changeCol.getAnim()))
            // Change
            Ui.sameLine(205 * player.changeCol.getAnim())
            Ui.textColored(player.getChangeColor(), player.getChangeString(player.changeCol.getAnim()))
            // Location
            Ui.sameLine(325)
            if (player.getCabinet().toInt() < 4 && !player.isIdle()) {
                when (player.getData().cabinetLoc.toInt()) {
                    0 -> Ui.textColored(Col4.RED_M, player.getCabinetString())
                    1 -> Ui.textColored(Col4.YLW_M, player.getCabinetString())
                    2 -> Ui.textColored(Col4.GRN_M, player.getCabinetString())
                    3 -> Ui.textColored(Col4.BLU_M, player.getCabinetString())
                    else -> Ui.textColored(Col4.GRY_L, player.getCabinetString())
                }
            } else Ui.textColored(Col4.GHOST, "-")
            Ui.sameLine(405)
            if (player.getCabinet().toInt() < 4 && !player.isIdle()) {
                when (player.getData().cabinetLoc.toInt()) {
                    0 -> Ui.textColored(Col4.RED_M, player.getPlaySideString())
                    1 -> Ui.textColored(Col4.YLW_M, player.getPlaySideString())
                    2 -> Ui.textColored(Col4.GRN_M, player.getPlaySideString())
                    3 -> Ui.textColored(Col4.BLU_M, player.getPlaySideString())
                    else -> Ui.textColored(Col4.GRY_L, player.getPlaySideString())
                }
            } else Ui.textColored(Col4.GHOST, "-")

            Ui.endGroup()

        }
    }



    private fun getLoadBarValue(player: Player):Float {
        when (player.getLoadPercent()) {
            0 -> return 0f
            100 -> return 0f
            else -> return player.getLoadPercent() * 0.01f
        }
    }

    var fighting = false
    private fun getLoadStatusString(player: Player):String {
        if (player.hasPlayed()) fighting = false
        if (player.isIdle()) return "Idle"
        when(player.getLoadPercent()) {
            0 -> return "Standby [${player.getIdle()}]"
            100 -> return if (fighting) "Fighting..." else "Standby [${player.getIdle()}]"
            else -> {
                fighting = true
                return "Loading ${player.getLoadPercent()}%"
            }
        }
    }
}