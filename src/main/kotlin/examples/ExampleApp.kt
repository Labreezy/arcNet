package examples

import glm_.f
import glm_.vec2.Vec2
import imgui.*
import imgui.imgui.demo.*
import imgui.imgui.demo.showExampleApp.*
import imgui.imgui.imgui_demoDebugInformations
import kotlin.reflect.KMutableProperty0

object ExampleApp {

    object show {
        // Examples Apps (accessible from the "Examples" menu)
        var documents = false
        var mainMenuBar = false
        var console = false
        var log = false
        var layout = false
        var propertyEditor = false
        var longText = false
        var autoResize = false
        var constrainedResize = false
        var simpleOverlay = false
        var windowTitles = false
        var customRendering = false

        // Dear ImGui Apps (accessible from the "Help" menu)
        var metrics = false
        var styleEditor = false
        var about = false
    }

    // Demonstrate the various window flags. Typically you would just use the default!
    var noTitlebar = false
    var noScrollbar = false
    var noMenu = false
    var noMove = false
    var noResize = false
    var noCollapse = false
    var noClose = false
    var noNav = false
    var noBackground = false
    var noBringToFront = false

    var filter = TextFilter()

    operator fun invoke(open_: KMutableProperty0<Boolean>?) {

        var open = open_

        if (ExampleApp.show.documents) Documents(show::documents);     // Process the Document app next, as it may also use a DockSpace()
        if (ExampleApp.show.mainMenuBar) MainMenuBar()
        if (ExampleApp.show.console) Console(show::console)
        if (ExampleApp.show.log) Log(show::log)
        if (ExampleApp.show.layout) SimpleLayout(show::layout)
        if (ExampleApp.show.propertyEditor) PropertyEditor(show::propertyEditor)
        if (ExampleApp.show.longText) LongText(show::longText)
        if (ExampleApp.show.autoResize) AutoResize(show::autoResize)
        if (ExampleApp.show.constrainedResize) ConstrainedResize(show::constrainedResize)
        if (ExampleApp.show.simpleOverlay) SimpleOverlay(show::simpleOverlay)
        if (ExampleApp.show.windowTitles) WindowTitles(show::windowTitles)
        if (ExampleApp.show.customRendering) CustomRendering(show::customRendering)
        if (ExampleApp.show.metrics) ImGui.showMetricsWindow(show::metrics)
        if (ExampleApp.show.styleEditor)
            functionalProgramming.withWindow("Style Editor", show::styleEditor) {
                StyleEditor()
            }

        if (ExampleApp.show.about)
            ImGui.showAboutWindow(show::about)

        var windowFlags = 0
        if (noTitlebar) windowFlags = windowFlags or WindowFlag.NoTitleBar
        if (noScrollbar) windowFlags = windowFlags or WindowFlag.NoScrollbar
        if (!noMenu) windowFlags = windowFlags or WindowFlag.MenuBar
        if (noMove) windowFlags = windowFlags or WindowFlag.NoMove
        if (noResize) windowFlags = windowFlags or WindowFlag.NoResize
        if (noCollapse) windowFlags = windowFlags or WindowFlag.NoCollapse
        if (noNav) windowFlags = windowFlags or WindowFlag.NoNav
        if (noBackground) windowFlags = windowFlags or WindowFlag.NoNav
        if (noBringToFront) windowFlags = windowFlags or WindowFlag.NoBringToFrontOnFocus
        if (noClose) open = null // Don't pass our bool* to Begin
        /*  We specify a default position/size in case there's no data in the .ini file. Typically this isn't required!
            We only do it to make the Demo applications a little more welcoming.         */
        ImGui.setNextWindowPos(Vec2(650, 20), Cond.FirstUseEver)
        ImGui.setNextWindowSize(Vec2(550, 680), Cond.FirstUseEver)

        // Main body of the Demo window starts here.
        if (!ImGui.begin_("ImGui Demo", open, windowFlags)) {
            ImGui.end()   // Early out if the window is collapsed, as an optimization.
            return
        }

        ImGui.text("dear imgui says hello. (${ImGui.version})")

        // Most "big" widgets share a common width settings by default.
        //pushItemWidth(windowWidth * 0.65f)    // Use 2/3 of the space for widgets and 1/3 for labels (default)
        // Use fixed width for labels (by passing a negative value), the rest goes to widgets. We choose a width proportional to our font size.
        ImGui.pushItemWidth(ImGui.fontSize * -12)

        // Menu
        functionalProgramming.menuBar {
            functionalProgramming.menu("Menu") { imgui_demoDebugInformations.showExampleMenuFile() }
//            stop = true
//            println("nav window name " + g.navWindow?.rootWindow?.name)
//            println("Examples")
            functionalProgramming.menu("Examples") {
                ImGui.menuItem("Main menu bar", "", show::mainMenuBar)
                ImGui.menuItem("Console", "", show::console)
                ImGui.menuItem("Log", "", show::log)
                ImGui.menuItem("Simple layout", "", show::layout)
                ImGui.menuItem("Property editor", "", show::propertyEditor)
                ImGui.menuItem("Long text display", "", show::longText)
                ImGui.menuItem("Auto-resizing window", "", show::autoResize)
                ImGui.menuItem("Constrained-resizing window", "", show::constrainedResize)
                ImGui.menuItem("Simple overlay", "", show::simpleOverlay)
                ImGui.menuItem("Manipulating window titles", "", show::windowTitles)
                ImGui.menuItem("Custom rendering", "", show::customRendering)
                ImGui.menuItem("Documents", "", show::documents)
            }
            functionalProgramming.menu("Help") {
                ImGui.menuItem("Metrics", "", show::metrics)
                ImGui.menuItem("Style Editor", "", show::styleEditor)
                ImGui.menuItem("About Dear ImGui", "", show::about)
            }
        }

        ImGui.spacing()

        functionalProgramming.collapsingHeader("Help") {
            ImGui.text("PROGRAMMER GUIDE:")
            ImGui.bulletText("Please see the ShowDemoWindow() code in imgui_demo.cpp. <- you are here!")
            ImGui.bulletText("Please see the comments in imgui.cpp.")
            ImGui.bulletText("Please see the examples/ in application.")
            ImGui.bulletText("Enable 'io.ConfigFlags |= NavEnableKeyboard' for keyboard controls.")
            ImGui.bulletText("Enable 'io.ConfigFlags |= NavEnableGamepad' for gamepad controls.")
            ImGui.separator()

            ImGui.text("USER GUIDE:")
            ImGui.showUserGuide()
        }

        functionalProgramming.collapsingHeader("Configuration") {

            functionalProgramming.treeNode("Configuration##2") {

                ImGui.checkboxFlags(
                    "io.ConfigFlags: NavEnableKeyboard",
                    ImGui.io::configFlags,
                    ConfigFlag.NavEnableKeyboard.i
                )
                ImGui.checkboxFlags(
                    "io.ConfigFlags: NavEnableGamepad",
                    ImGui.io::configFlags,
                    ConfigFlag.NavEnableGamepad.i
                )
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Required back-end to feed in gamepad inputs in io.NavInputs[] and set io.BackendFlags |= ImGuiBackendFlags_HasGamepad.\n\nRead instructions in imgui.cpp for details.")
                ImGui.checkboxFlags(
                    "io.ConfigFlags: NavEnableSetMousePos",
                    ImGui.io::configFlags,
                    ConfigFlag.NavEnableSetMousePos.i
                )
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Instruct navigation to move the mouse cursor. See comment for ImGuiConfigFlags_NavEnableSetMousePos.")
                ImGui.checkboxFlags("io.ConfigFlags: NoMouse", ImGui.io::configFlags, ConfigFlag.NoMouse.i)
                if (ImGui.io.configFlags has ConfigFlag.NoMouse) { // Create a way to restore this flag otherwise we could be stuck completely!

                    if ((ImGui.time.f % 0.4f) < 0.2f) {
                        ImGui.sameLine()
                        ImGui.text("<<PRESS SPACE TO DISABLE>>")
                    }
                    if (Key.Space.isPressed)
                        ImGui.io.configFlags = ImGui.io.configFlags wo ConfigFlag.NoMouse
                }
                ImGui.checkboxFlags(
                    "io.ConfigFlags: NoMouseCursorChange",
                    ImGui.io::configFlags,
                    ConfigFlag.NoMouseCursorChange.i
                )
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Instruct back-end to not alter mouse cursor shape and visibility.")
                ImGui.checkbox("io.ConfigCursorBlink", ImGui.io::configInputTextCursorBlink)
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Set to false to disable blinking cursor, for users who consider it distracting")
                ImGui.checkbox("io.ConfigWindowsResizeFromEdges", ImGui.io::configWindowsResizeFromEdges)
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Enable resizing of windows from their edges and from the lower-left corner.\nThis requires (io.BackendFlags & ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.")
                ImGui.checkbox("io.configWindowsMoveFromTitleBarOnly", ImGui.io::configWindowsMoveFromTitleBarOnly)
                ImGui.checkbox("io.MouseDrawCursor", ImGui.io::mouseDrawCursor)
                ImGui.sameLine(); imgui_demoDebugInformations.showHelpMarker("Instruct Dear ImGui to render a mouse cursor for you. Note that a mouse cursor rendered via your application GPU rendering path will feel more laggy than hardware cursor, but will be more in sync with your other visuals.\n\nSome desktop applications may use both kinds of cursors (e.g. enable software cursor only when resizing/dragging something).")
                ImGui.separator()
            }
            functionalProgramming.treeNode("Backend Flags") {
                imgui_demoDebugInformations.showHelpMarker("Those flags are set by the back-ends (imgui_impl_xxx files) to specify their capabilities.")
                val backendFlags =
                    intArrayOf(ImGui.io.backendFlags) // Make a local copy to avoid modifying the back-end flags.
                ImGui.checkboxFlags("io.BackendFlags: HasGamepad", backendFlags, BackendFlag.HasGamepad.i)
                ImGui.checkboxFlags("io.BackendFlags: HasMouseCursors", backendFlags, BackendFlag.HasMouseCursors.i)
                ImGui.checkboxFlags("io.BackendFlags: HasSetMousePos", backendFlags, BackendFlag.HasSetMousePos.i)
                ImGui.separator()
            }

            functionalProgramming.treeNode("Style") {
                StyleEditor()
                ImGui.separator()
            }

            functionalProgramming.treeNode("Capture/Logging") {
                ImGui.textWrapped("The logging API redirects all text output so you can easily capture the content of a window or a block. Tree nodes can be automatically expanded.")
                imgui_demoDebugInformations.showHelpMarker("Try opening any of the contents below in this window and then click one of the \"Log To\" button.")
                ImGui.logButtons()
                ImGui.textWrapped("You can also call ImGui::LogText() to output directly to the log without a visual output.")
                if (ImGui.button("Copy \"Hello, world!\" to clipboard")) {
                    ImGui.logToClipboard()
                    ImGui.logText("%s", "Hello, world!")
                    ImGui.logFinish()
                }
            }
        }

        functionalProgramming.collapsingHeader("Window options") {
            ImGui.checkbox("No titlebar", ExampleApp::noTitlebar); ImGui.sameLine(150)
            ImGui.checkbox("No scrollbar", ExampleApp::noScrollbar); ImGui.sameLine(300)
            ImGui.checkbox("No menu", ExampleApp::noMenu)
            ImGui.checkbox("No move", ExampleApp::noMove); ImGui.sameLine(150)
            ImGui.checkbox("No resize", ExampleApp::noResize); ImGui.sameLine(300)
            ImGui.checkbox("No collapse", ExampleApp::noCollapse)
            ImGui.checkbox("No close", ExampleApp::noClose); ImGui.sameLine(150)
            ImGui.checkbox("No nav", ExampleApp::noNav); ImGui.sameLine(300)
            ImGui.checkbox("No background", ExampleApp::noBackground)
            ImGui.checkbox("No bring to front", ExampleApp::noBringToFront)
        }

        showDemoWindowWidgets()
        showDemoWindowLayout()
        showDemoWindowPopups()
        showDemoWindowColumns()


        showDemoWindowMisc()

        // End of ShowDemoWindow()
        ImGui.end()
    }
}

