import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.Cond
import imgui.ImGui
import imgui.imgui.demo.showExampleApp.PlayerOverlay
import imgui.or
import imgui.WindowFlag as Wf

val WINDOW_NAME = "arcNet  -  DISCONNECTED \uD83D\uDCE1 ..."
val WINDOW_TINT = Vec4(0.16f, 0.16f, 0.16f, 1.0f)
val WINDOW_HORZ = 640
val WINDOW_VERT = 480
var yup = true

fun runApplication() {
    ImGui.run {
        setNextWindowPos(Vec2(0, 0), Cond.Always)
        setNextWindowSize(Vec2(WINDOW_HORZ, WINDOW_VERT), Cond.Always)
        begin("", null, 0 or Wf.NoTitleBar or Wf.NoResize or Wf.NoCollapse or Wf.NoBackground)
        text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.framerate, io.framerate)
        end()
        PlayerOverlay.invoke(::yup)
    }
}

