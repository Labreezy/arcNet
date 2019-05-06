import azUtils.pathHome
import javafx.beans.property.SimpleStringProperty
import javafx.css.converter.FontConverter
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import org.intellij.lang.annotations.JdkConstants
import session.Session
import tornadofx.*

private val session: Session = Session()
fun getSession() = session

fun main(args: Array<String>) { launch<MyApp>(args) }
class MyApp: App(MyView::class, Styles::class) { init { reloadStylesheetsOnFocus() }
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 960.0
        stage.height = 720.0
        stage.isResizable = false
    }
}

class MyView : View() {
    val controller: MyController by inject()
    val input = SimpleStringProperty()

    override val root = form {
        addClass(Styles.wrapper)
        label("it's a label") {
            addClass(Styles.alice)
            text = getSession().xrdApi.isConnected().toString()
        }
        label("it's another label") {
            addClass(Styles.bob)
            text = getSession().xrdApi.isConnected().toString()
        }
        fieldset {
            field("Input") {
                input.value = ""
                textfield(input)
            }

            button("Commit") {
                action {
                    if (!input.value.isEmpty()) {
                        controller.writeToDb(input.value)
                        input.value = ""
                    }
                }
                addClass(Styles.dangerButton)
            }
            piechart("Desktop/Laptop OS Market Share") {
                data("Windows", 77.62)
                data("OS X", 9.52)
                data("Other", 3.06)
                data("Linux", 1.55)
                data("Chrome OS", 0.55)
            }
        }
        val testImage = "${pathHome.toUri().toURL()}src/main/resources/ino_test.png"

            imageview(testImage) {
                setPrefSize(512.0, 512.0)
            }
    }
}


class MyController: Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }
}


class Styles : Stylesheet() {
    companion object {
        // Define our styles
        val wrapper by cssclass()
        val bob by cssclass()
        val alice by cssclass()
        val dangerButton by cssclass()

        // Define our colors
        val dangerColor = c("#a94442")
        val hoverColor = c("#d49942")
    }

    init {
        dangerButton {
            minWidth = 200.px
            padding = box(16.px)
            backgroundInsets += box(4.px)
            font = Font.font("paladins")
            fontSize = 20.px
        }

        wrapper {
            padding = box(40.px)
            spacing = 40.px
            backgroundColor += c("#111111")
        }

        label {
            fontSize = 16.px
            padding = box(5.px, 10.px)
            maxWidth = infinity
            textFill = c("#666666")

            and(bob, alice) {
                borderColor += box(dangerColor)
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.MITER, StrokeLineCap.BUTT, 10.0, 0.0, listOf(25.0, 5.0))
                borderWidth += box(5.px)

                and(hover) {
                    backgroundColor += hoverColor
                }
            }
        }
    }
}