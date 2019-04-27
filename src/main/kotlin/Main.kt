import application.*
import glm_.vec4.Vec4
import gln.checkError
import gln.glClearColor
import gln.glViewport
import imgui.Context
import imgui.DEBUG
import imgui.ImGui as Ui
import imgui.impl.LwjglGlfw
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.Platform
import uno.glfw.GlfwWindow
import uno.glfw.VSync
import uno.glfw.glfw

fun main(args: Array<String>) {
    Start_lwjgl()
}

lateinit var window: GlfwWindow
val WINDOW_TINT = Vec4(0.26f, 0.06f, 0.16f, 1.0f)
val WINDOW_HORZ = 720
val WINDOW_VERT = 600

private class Start_lwjgl {

    val context: Context
    val lwjglGlfw: LwjglGlfw

    init {
        glfw.init("3.0")
        DEBUG = false
        window = GlfwWindow(WINDOW_HORZ, WINDOW_VERT, "").apply { init() }
        window.resizable = false
        window.decorated = true
        glfw.swapInterval = VSync.ON
        context = Context()
        imgui.ImGui.styleColorsDark()
        lwjglGlfw = LwjglGlfw(window, true, LwjglGlfw.GlfwClientApi.OpenGL)
        window.loop(::mainLoop)
        lwjglGlfw.shutdown()
        context.destroy()
        window.destroy()
        glfw.terminate()

    }

    fun mainLoop(stack: MemoryStack) {
        lwjglGlfw.newFrame()
        Ui.run { newFrame(); runApplicationLoop() }
        Ui.render()
        glViewport(window.framebufferSize)
        glClearColor(WINDOW_TINT)
        glClear(GL_COLOR_BUFFER_BIT)
        lwjglGlfw.renderDrawData(Ui.drawData!!)
        if (DEBUG) checkError("mainLoop")
    }

}