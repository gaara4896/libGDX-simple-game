package my.game.pkg

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera

class MainMenuScreen(val game:Test) extends Screen{

	var camera:OrthographicCamera = new OrthographicCamera
	camera.setToOrtho(false, 800, 480)

	override def render(delta:Float){
		Gdx.gl.glClearColor(0, 0, 0.2f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		camera.update
		game.batch.setProjectionMatrix(camera.combined)

		game.batch.begin
		game.font.draw(game.batch, "Welcome to Drop!!!", 100, 150)
		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 120)
		game.batch.end

		if(Gdx.input.isTouched()){
			game.setScreen(new GameScreen(game))
			dispose
		}
	}

	override def resize(width:Int, height:Int){}

	override def show(){}

	override def hide(){}

	override def pause(){}

	override def resume(){}

	override def dispose(){}
}