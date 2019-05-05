import application.WINDOW_HORZ
import application.WINDOW_TINT
import application.WINDOW_VERT
import application.runApplicationLoop
import gln.checkError
import gln.glClearColor
import gln.glViewport
import imgui.Context
import imgui.DEBUG
import imgui.impl.LwjglGlfw
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.system.MemoryStack
import uno.glfw.GlfwWindow
import uno.glfw.glfw
import imgui.ImGui as Ui

fun main(args: Array<String>) {
    Start_lwjgl()
}

lateinit var window: GlfwWindow

private class Start_lwjgl {

    val context: Context
    val lwjglGlfw: LwjglGlfw

    init {
        glfw.init("3.0")
        DEBUG = false

        window = GlfwWindow(WINDOW_HORZ, WINDOW_VERT, "").apply { init() }
        window.resizable = false
        window.decorated = true
        context = Context()
        Ui.styleColorsDark()
        lwjglGlfw = LwjglGlfw(window, true, LwjglGlfw.GlfwClientApi.OpenGL)

        window.loop { mainLoop(it) }

        lwjglGlfw.shutdown()
        context.destroy()
        window.destroy()
        glfw.terminate()

    }

    fun mainLoop(stack: MemoryStack) {
        lwjglGlfw.newFrame()
        Ui.run { newFrame(); runApplicationLoop(stack) }
        Ui.render()
        glViewport(window.framebufferSize)
        glClearColor(WINDOW_TINT)
        glClear(GL_COLOR_BUFFER_BIT)
        lwjglGlfw.renderDrawData(Ui.drawData!!)
        if (DEBUG) checkError("mainLoop")
    }

}
