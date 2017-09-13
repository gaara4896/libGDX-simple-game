package my.game.pkg

import com.badlogic.gdx.backends.lwjgl._

object Main extends App {
    val config = new LwjglApplicationConfiguration
    config.title = "Drop"
    config.height = 480
    config.width = 800
    config.forceExit = false
    new LwjglApplication(new Test, config)
}
