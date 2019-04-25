import glm_.vec4.Vec4
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

fun main(args: Array<String>) {
    HelloWorld_lwjgl()
}

private class HelloWorld_lwjgl {

    val window: GlfwWindow
    val ctx: Context


    var f = 0f
    val clearColor = Vec4(0.45f, 0.55f, 0.6f, 1f)
    var showAnotherWindow = false
    var showDemo = true
    var counter = 0

    val lwjglGlfw: LwjglGlfw

    init {

        glfw.init(if (Platform.get() == Platform.MACOSX) "3.2" else "3.0")

        window = GlfwWindow(1280, 720, "arcNet").apply {
            init()
        }

        glfw.swapInterval = VSync.ON   // Enable vsync

        ctx = Context()

        ImGui.styleColorsDark()


        // Setup Platform/Renderer bindings
        lwjglGlfw = LwjglGlfw(window, true, LwjglGlfw.GlfwClientApi.OpenGL)

        window.loop(::mainLoop)
        lwjglGlfw.shutdown()
        ctx.destroy()

        window.destroy()
        glfw.terminate()
    }

    fun mainLoop(stack: MemoryStack) {
        lwjglGlfw.newFrame()
        ImGui.run {

            newFrame()
            if (showDemo)
                showDemoWindow(::showDemo)

            run {

                begin("Hello, world!")                          // Create a window called "Hello, world!" and append into it.

                text("This is some useful text.")                // Display some text (you can use a format strings too)
                checkbox("Demo Window", ::showDemo)             // Edit bools storing our window open/close state
                checkbox("Another Window", ::showAnotherWindow)

                sliderFloat("float", ::f, 0f, 1f)   // Edit 1 float using a slider from 0.0f to 1.0f
                colorEdit3("clear color", clearColor)           // Edit 3 floats representing a color

                if (button("Button"))                           // Buttons return true when clicked (most widgets return true when edited/activated)
                    counter++

                /*  Or you can take advantage of functional programming and pass directly a lambda as last parameter:
                    button("Button") { counter++ }                */

                sameLine()
                text("counter = $counter")

                text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.framerate, io.framerate)

                end()

                // 3. Show another simple window.
                if (showAnotherWindow) {
                    // Pass a pointer to our bool variable (the window will have a closing button that will clear the bool when clicked)
                    begin_("Another Window", ::showAnotherWindow)
                    text("Hello from another window!")
                    if (button("Close Me"))
                        showAnotherWindow = false
                    end()
                }
            }
        }

        // Rendering
        ImGui.render()
        glViewport(window.framebufferSize)
        glClearColor(clearColor)
        glClear(GL_COLOR_BUFFER_BIT)

        lwjglGlfw.renderDrawData(ImGui.drawData!!)

        if (DEBUG)
            checkError("mainLoop")

//        RemoteryGL.rmt_EndOpenGLSample()
    }
}