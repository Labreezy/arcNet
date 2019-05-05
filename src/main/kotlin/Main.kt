import azUtils.pathHome
import javafx.beans.property.SimpleStringProperty
import javafx.scene.image.Image
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import session.Session
import tornadofx.*

private val session: Session = Session()
fun getSession() = session

fun main(args: Array<String>) {
    launch<MyApp>(args)
}

class MyApp: App(MyView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

class MyView : View() {
    val controller: MyController by inject()
    val input = SimpleStringProperty()

    override val root = form {
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
        println(testImage)
        run {
            val start = System.currentTimeMillis()
            imageview(testImage) {

                setPrefSize(512.0, 512.0)
                println("loaded for ${System.currentTimeMillis() - start} msecs")
            }
            println("finished after ${System.currentTimeMillis() - start} msecs")
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

        // Define our colors
        val dangerColor = c("#a94442")
        val hoverColor = c("#d49942")
    }

    init {
        wrapper {
            padding = box(10.px)
            spacing = 10.px
        }

        label {
            fontSize = 16.px
            padding = box(5.px, 10.px)
            maxWidth = infinity

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