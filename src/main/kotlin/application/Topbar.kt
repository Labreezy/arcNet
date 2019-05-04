package application

import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.ImGui as Ui
import session.Session

object Topbar {
    fun generateFunctionButtons(session: Session) {
        Ui.button("Func 0", Vec2(120, 24))
        Ui.sameLine(360)
        if (session.xrdApi.isConnected()) Ui.textColored(Vec4(0, 1, 0, 0.8), "[ Gear ONLINE ] ${getMemoryCycleDots(session)}")
        else Ui.textColored(Vec4(1, 0, 0, 0.4), "[ Gear OFFLINE ]")
        Ui.sameLine(500)
        Ui.textColored(Vec4(0, 1, 0, 0.8), "[ Cradle ONLINE ]")
        Ui.sameLine(628)
        if (session.dataApi.isConnected()) Ui.textColored(Vec4(0, 1, 0, 0.8), "${getDatabaseCycleDots(session)} [ Justice ONLINE ]")
        else Ui.textColored(Vec4(1, 0, 0, 0.4), "[ Justice OFFLINE ]")
    }

    private fun getMemoryCycleDots(session: Session):String {
        when (session.memoryCycle) {
            1 -> return ".  "
            2 -> return ".. "
            3 -> return "..."
            4 -> return " .."
            5 -> return "  ."
            6 -> return "   "
            else -> session.memoryCycle = 1
        }
        return "   "
    }

    private fun getDatabaseCycleDots(session: Session):String {
        when (session.databaseCycle) {
            1 -> return "  ."
            2 -> return " .."
            3 -> return "..."
            4 -> return ".. "
            5 -> return ".  "
            6 -> return "   "
            else -> session.databaseCycle = 1
        }
        return "   "
    }
}