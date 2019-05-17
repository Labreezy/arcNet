import application.MainStyle
import application.MainView
import application.match.MatchStyle
import application.player.PlayerStyle
import javafx.stage.Stage
import tornadofx.App
import tornadofx.UIComponent
import tornadofx.launch

fun main(args: Array<String>) { launch<MyApp>(args) }

class MyApp : App(MainView::class, MainStyle::class, MatchStyle::class, PlayerStyle::class) {

    init {
//        reloadStylesheetsOnFocus()
    }

    override fun onBeforeShow(view: UIComponent) {
        super.onBeforeShow(view)
        view.title = "ＧｅａｒＮｅｔ  //  0.5.6"

    }

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width  = 976.0 // 960
        stage.height = 759.0 // 720
        stage.isResizable = false
    }

}