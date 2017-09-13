package my.game.pkg

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Test extends Game{

    var batch:SpriteBatch = null
    var font:BitmapFont = null

    override def create(){
        batch = new SpriteBatch
        font = new BitmapFont
        this.setScreen(new MainMenuScreen(this))
    }

    override def render(){
        super.render
    }

    override def dispose(){
        batch.dispose
        font.dispose
    }

}