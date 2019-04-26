package imgui.imgui.demo.showExampleApp

import gli_.has
import glm_.vec2.Vec2
import imgui.*
import imgui.ImGui.font
import imgui.ImGui.fontSize
import imgui.ImGui.io
import imgui.ImGui.isMousePosValid
import imgui.ImGui.menuItem
import imgui.ImGui.separator
import imgui.ImGui.setNextWindowBgAlpha
import imgui.ImGui.setNextWindowContentSize
import imgui.ImGui.setNextWindowPos
import imgui.ImGui.text
import imgui.functionalProgramming.menuItem
import imgui.functionalProgramming.popupContextWindow
import imgui.functionalProgramming.withWindow
import org.lwjgl.openvr.CompositorOverlaySettings.DISTANCE
import kotlin.reflect.KMutableProperty0
import imgui.ColorEditFlag as Cef
import imgui.InputTextFlag as Itf
import imgui.SelectableFlag as Sf
import imgui.TreeNodeFlag as Tnf
import imgui.WindowFlag as Wf

object PlayerOverlay {

    var corner = 0

    /** Demonstrate creating a simple static window with no decoration + a context-menu to choose which corner
     *  of the screen to use */
    operator fun invoke(open: KMutableProperty0<Boolean>) {

        val DISTANCE = 32f
        var flags = Wf.NoTitleBar or Wf.NoResize or Wf.AlwaysAutoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoNav
        if (corner != -1) {
            val windowPos = Vec2{ if (corner has it + 1) io.displaySize[it] - DISTANCE else DISTANCE }
            val windowPosPivot = Vec2(if (corner has 1) 1f else 0f, if (corner has 2) 1f else 0f)
            setNextWindowPos(windowPos, Cond.Always, windowPosPivot)
        }
        setNextWindowBgAlpha(0.4f)  // Transparent background
        setNextWindowContentSize( Vec2(400f,80f))
        withWindow("title", open, flags) {
            text("aze  -  [ ID 1234567890 ]")
            separator()
            ImGui.pushItemWidth(fontSize * -12)
            text("Slayer")
            text("Chain: 4  [ W:12 / M:32 ]")
            ImGui.sameLine(220)
            text("Bounty: 123,000,100 W$")
            text("Mouse Position: " + when {
                isMousePosValid() -> "(%.1f,%.1f)".format(io.mousePos.x, io.mousePos.y)
                else -> "<invalid>"
            })
        }
    }
}