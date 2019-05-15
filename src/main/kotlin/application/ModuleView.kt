package application

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import tornadofx.*

class ModuleView(override val root: Parent, modTitle: String): Fragment() {

    private lateinit var backdrop: ImageView
    private lateinit var moduleName: Label
    private var frame = 0
    private var enabled = true

    init { with(root) {
            stackpane {
                maxHeight = 32.0
                backdrop = imageview(getRes("gn_atlas.png").toString()) { viewport = Rectangle2D(512.0, if (enabled)  256.0 else 288.0, 128.0, 32.0) }
                moduleName = label(modTitle) { addClass(MainStyle.moduleTitle) }
            }
        } }

    fun nextFrame() = Platform.runLater {
        if (frame++ > 4) frame = 4
        if (enabled) moduleName.textFill = c("#8ddf4fee")
        else moduleName.textFill = c("#c14e2eee")
        when (frame) {
            1 -> backdrop.viewport = Rectangle2D(512.0, if (enabled) 256.0 else 288.0, 128.0, 32.0)
            2 -> backdrop.viewport = Rectangle2D(640.0, if (enabled) 256.0 else 288.0, 128.0, 32.0)
            3 -> backdrop.viewport = Rectangle2D(768.0, if (enabled) 256.0 else 288.0, 128.0, 32.0)
            4 -> backdrop.viewport = Rectangle2D(896.0, if (enabled) 256.0 else 288.0, 128.0, 32.0)
            else -> backdrop.viewport = Rectangle2D(896.0, if (enabled) 256.0 else 288.0, 128.0, 32.0)
        }
    }

    fun reset(connected:Boolean) {
        enabled = connected
        frame = 0
    }

}