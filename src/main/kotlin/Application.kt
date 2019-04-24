import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import tornadofx.Stylesheet.Companion.label

class Application: App(MainViewport::class)

fun main(args: Array<String>) = launch<Application>(args)

class MainViewport: View() {
    override val root = vbox {
        button("Press me") { spacing = 8.0 }
        label("Waiting") { spacing = 8.0 }
    }
}