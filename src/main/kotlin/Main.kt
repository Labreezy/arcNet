import azUtils.pathHome
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.HBox
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
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
        // TOP BAR
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
        // PLAYER VIEW
        hbox {
            addClass(Styles.playerview)
            imageview("${pathHome.toUri().toURL()}src/main/resources/gn_atlas.png") {
                setPreserveRatio(true)
                setViewport(Rectangle2D(0.0, 0.0, 64.0, 64.0))
                setPrefSize(64.0, 64.0)
            }
            vbox {
                alignment = Pos.CENTER_LEFT
                label("EL GRANDE HANDLE DE TEJAS") { addClass(Styles.handle) }
                label("2,876,050 W$") { addClass(Styles.bounty) }
            }
            vbox {
                alignment = Pos.CENTER_RIGHT
                hbox {
                    progressbar {
                        minWidth = 192.0
                        maxHeight = 16.0
                    }
                }
                hbox {
                    addClass(Styles.playersection)
                    vbox {
                        label("Rating: A") { addClass(Styles.rating) }
                        label("Chains: 8") { addClass(Styles.chains) }
                    }
                    vbox {
                        label("Wins: 80") { addClass(Styles.rating) }
                        label("Games: 160") { addClass(Styles.chains) }
                    }
                }
            }
        }
        // BOTTOM SOMETHING ???
        label("it's a label") {
            addClass(Styles.title)
            text = getSession().xrdApi.isConnected().toString()
        }
    }
}



class Styles : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val playerview by cssclass()
        val playersection by cssclass()
        val handle by cssclass()
        val bounty by cssclass()
        val rating by cssclass()
        val chains by cssclass()
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

        playerview {
            borderWidth += box(2.px)
            borderColor += box(c("#34081c"))
            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
            backgroundColor += c("#23041288")
            padding = box(2.px)
            maxWidth = 512.px
        }
        playersection {
//            borderWidth += box(1.px)
//            borderColor += box(c("#34081c"))
//            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
            maxWidth = infinity
            minWidth = 192.px
            padding = box(0.px)
        }

        label {
//
//            borderColor += box(c("#113322CC"))
//            backgroundColor += c("#11332288")
//            borderWidth += box(1.px)
//            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))

            fontSize = 14.px
            padding = box(2.px, 4.px)
            maxWidth = infinity
            textFill = c("#cccccc")
            spacing = 8.px
            maxWidth = 96.px
            minWidth = 96.px

            and(handle) {
                fontSize = 16.px
                padding = box(-2.px, 0.px, 0.px, 8.px)
                textFill = c("#33ff33")
                fontStyle = FontPosture.ITALIC
                fontWeight = FontWeight.BOLD
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(bounty) {
                fontSize = 40.px
                padding = box(-12.px, 10.px, 4.px, 8.px)
                textFill = c("#ffcc33")
                fontFamily = "RED"
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(rating) {
                fontSize = 12.px
            }
            and(chains) {
                fontSize = 12.px
            }
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




class MyController: Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }
}