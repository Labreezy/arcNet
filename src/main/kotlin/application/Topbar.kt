package application

import glm_.vec2.Vec2
import glm_.vec4.Vec4
import objects.Col4
import imgui.ImGui as Ui
import session.Session

object Topbar {
    fun generateFunctionButtons(session: Session) {
        Ui.button("Func 0", Vec2(120, 24))
        Ui.sameLine(410)
        Ui.textColored(Col4.online(session.xrdApi.isConnected()), "[ Guilty Gear ] ${getMemoryCycleDots(session)}")
        Ui.sameLine(556)
        Ui.textColored(Vec4(0, 1, 0, 0.8), "[ GearNet ]")
        Ui.sameLine(650)
        Ui.textColored(Col4.online(session.dataApi.isConnected()), "${getDatabaseCycleDots(session)} [ SQL Database ]")

    }

    private fun getMemoryCycleDots(session: Session):String {
        if (session.xrdApi.isConnected()) {
            when (session.memoryCycle) {
                1 -> return ".  "
                2 -> return ".. "
                3 -> return "..."
                4 -> return " .."
                5 -> return "  ."
                6 -> return "   "
                else -> session.memoryCycle = 1
            }
        }
        return "   "
    }

    private fun getDatabaseCycleDots(session: Session):String {
        if (session.dataApi.isConnected()) {
            when (session.databaseCycle) {
                1 -> return "  ."
                2 -> return " .."
                3 -> return "..."
                4 -> return ".. "
                5 -> return ".  "
                6 -> return "   "
                else -> session.databaseCycle = 1
            }
        }
        return "   "
    }
}