import com.jogamp.opengl.util.texture.spi.DDSImage.D3DFMT_UNKNOWN
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import graphics.scenery.spirvcrossj.SPIRType.BaseType.Image
import imgui.Cond
import imgui.ImGui
import imgui.WindowFlag
import imgui.or

val WINDOW_NAME = "arcNet  -  DISCONNECTED \uD83D\uDCE1 ..."
val WINDOW_TINT = Vec4(0.16f, 0.16f, 0.16f, 1.0f)
val WINDOW_HORZ = 640
val WINDOW_VERT = 480

fun runApplication() {
    ImGui.run {
        setNextWindowPos(Vec2(0, 0), Cond.Always)
        setNextWindowSize(Vec2(WINDOW_HORZ, WINDOW_VERT), Cond.Always)
        begin("", null, 0 or WindowFlag.NoTitleBar or WindowFlag.NoResize or WindowFlag.NoCollapse or WindowFlag.NoBackground)
        text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.framerate, io.framerate)

        end()
    }
}

