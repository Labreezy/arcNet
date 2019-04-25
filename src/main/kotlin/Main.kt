import gln.checkError
import gln.glClearColor
import gln.glViewport
import imgui.Context
import imgui.DEBUG
import imgui.ImGui
import imgui.impl.LwjglGlfw
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.Platform
import uno.glfw.GlfwWindow
import uno.glfw.VSync
import uno.glfw.glfw

fun main(args: Array<String>) { Start_lwjgl() }

private class Start_lwjgl {

    val window: GlfwWindow
    val context: Context
    val lwjglGlfw: LwjglGlfw

    init {
        glfw.init(if (Platform.get() == Platform.MACOSX) "3.2" else "3.0")
        window = GlfwWindow(WINDOW_HORZ, WINDOW_VERT, WINDOW_NAME).apply { init() }
        window.resizable = false
        window.decorated = true
        glfw.swapInterval = VSync.ON
        context = Context()
        ImGui.styleColorsDark()
        lwjglGlfw = LwjglGlfw(window, true, LwjglGlfw.GlfwClientApi.OpenGL)
        window.loop(::mainLoop)
        lwjglGlfw.shutdown()
        context.destroy()
        window.destroy()
        glfw.terminate()
    }

    fun mainLoop(stack: MemoryStack) {
        lwjglGlfw.newFrame()
        ImGui.run { newFrame(); runApplication() }
        ImGui.render()
        glViewport(window.framebufferSize)
        glClearColor(WINDOW_TINT)
        glClear(GL_COLOR_BUFFER_BIT)
        lwjglGlfw.renderDrawData(ImGui.drawData!!)
        if (DEBUG) checkError("mainLoop")
    }

}