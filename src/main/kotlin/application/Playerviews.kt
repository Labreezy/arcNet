package application

import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.*
import objects.Col4
import session.Player

object Playerviews {
    private val CELL_FLAGS = WindowFlag.NoTitleBar or WindowFlag.NoCollapse or WindowFlag.NoScrollbar or WindowFlag.NoResize or WindowFlag.NoSavedSettings

    fun generatePlayerView(player: Player, height: Float) {
        ImGui.setNextWindowPos(Vec2(ImGui.io.displaySize[0].toFloat() - 550f, (70f * height) + 40f), Cond.Always, Vec2(0f))
        ImGui.setNextWindowSize(Vec2(550f, 70f))
        ImGui.setNextWindowBgAlpha(0.8f)
        ImGui.pushStyleVar(StyleVar.WindowRounding, 0f)
        functionalProgramming.withWindow("title${height}", null, CELL_FLAGS) {
            // Portrait
            ImGui.image(2, Vec2(40,52))
            ImGui.sameLine(60)
            ImGui.beginGroup()

            // ------------------ TOP ROW ------------------
            // Player
            ImGui.textColored(if (player.present) Col4.ONLINE else Col4.OFFLINE, player.getNameString())
            // Rating
            ImGui.sameLine(230)
            ImGui.textColored(statColor(player, (player.getRating()*10).toInt(), Col4.GRAY), "Rating:")
            ImGui.sameLine(285)
            ImGui.textColored(player.getRatingColor(), player.getRatingLetter())
            // Status
            ImGui.sameLine(325)
            ImGui.pushItemWidth(ImGui.calcItemWidth()/3)
            if (!player.isIdle()) ImGui.progressBar(getLoadBarValue(player),  Vec2(160, 16), getLoadStatusString(player))
            else ImGui.progressBar(0f, Vec2(160, 16), "Idle")

            // ------------------ MID ROW ------------------
            // Character
            ImGui.textColored(if (player.present) Col4.BLUE else Col4.BLUE_DK, player.getCharacter(false))
            // Chains
            ImGui.sameLine(230)
            ImGui.textColored(statColor(player, player.getChain(), Col4.GRAY), "Chains:")
            ImGui.sameLine(285)
            ImGui.textColored(statColor(player, player.getChain(), Vec4(0.2, 1, 0.8, 1)), player.getChainString())
            // Location
            ImGui.sameLine(330)
            if (player.getCabinet().toInt() < 4 && !player.isIdle()) {
                when (player.getData().playerSide.toInt()) {
                    0 -> ImGui.textColored(Vec4(0.8, 0.2, 0.2, 1), player.getCabinetString())
                    1 -> ImGui.textColored(Vec4(0.1, 0.5, 0.8, 1), player.getCabinetString())
                    2 -> ImGui.textColored(Vec4(0.7, 0.6, 0.0, 1), player.getCabinetString())
                    else -> ImGui.textColored(Col4.GRAY, player.getCabinetString())
                }
            } else ImGui.textColored(Col4.GHOST, "-")

            // ------------------ BOT ROW ------------------
            // Bounty
            ImGui.textColored(statColor(player, player.getBounty(), Col4.GOLD), player.getBountyString())
            // Change
            ImGui.sameLine(224)
            ImGui.textColored(if (player.getChange()>0) Col4.GREEN else Col4.RED, player.getChangeString())
            // Record
            ImGui.sameLine(330)
            ImGui.textColored(statColor(player, player.getMatchesPlayed(), Col4.GRAY), player.getRecordString())
            ImGui.endGroup()
        }
    }

    private fun statColor(player: Player, value:Int, vec4: Vec4): Vec4 {
        if (!player.present || value <= 0) return Vec4(1,1,1,0.4)
        return vec4
    }

    private fun getLoadBarValue(player: Player):Float {
        when (player.getLoadPercent()) {
            0 -> return 0f
            100 -> return 0f
            else -> return player.getLoadPercent() * 0.01f
        }
    }

    private fun getLoadStatusString(player: Player):String {
        when(player.getLoadPercent()) {
            0 -> return "Standby [${player.getIdle()}]"
            100 -> return "Standby [${player.getIdle()}]"
            else -> return "Loading ${player.getLoadPercent()}%"
        }
    }
}