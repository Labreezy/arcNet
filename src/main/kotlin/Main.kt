import azUtils.pathHome
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.HBox
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.stage.Stage
import session.Session
import tornadofx.*
import java.net.URI

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
        hbox {
            alignment = Pos.CENTER
            label("GuiltyGear") {
                if (getSession().xrdApi.isConnected()) addClass(Styles.online)
                else addClass(Styles.title)
            }
            label("GearNet") {
                addClass(Styles.online)
            }
            label("Database") {
                if (getSession().dataApi.isConnected()) addClass(Styles.online)
                else addClass(Styles.title)
            }
            children.filter { it is HBox }.addClass(title)
        }
        label("it's a label") {
            addClass(Styles.title)
            text = getSession().xrdApi.isConnected().toString()
        }
        imageview("${pathHome.toUri().toURL()}src/main/resources/gn_atlas.png") {
            setPreserveRatio(true)
            setViewport(Rectangle2D(512.0,0.0,64.0,64.0))
            setPrefSize(64.0, 64.0)
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
        val wrapper by cssclass()
        val title by cssclass()
        val online by cssclass()
    }

    init {
        wrapper {
            val myURI = URI("${pathHome.toUri().toURL()}src/main/resources/gn_background.png")
            padding = box(16.px)
            spacing = 8.px
            backgroundImage += myURI
        }

        label {
            fontSize = 16.px
            padding = box(5.px, 10.px)
            maxWidth = infinity
            textFill = c("#cccccc")

            and(title) {
                backgroundColor += c("#222222")
                borderColor += box(c("#34081c"))
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
                borderWidth += box(2.px)
            }
            and(online) {
                backgroundColor += c("#222222")
                borderColor += box(c("#fd9a26"))
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
                borderWidth += box(2.px)
            }
        }
    }
}