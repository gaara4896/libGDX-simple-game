package my.game.pkg

import java.util.Iterator

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.Array

class Test extends Game {

	private var dropImage:Texture = null
	private var bucketImage:Texture = null
	private var dropSound:Sound = null
	private var rainMusic:Music = null
	private var batch:SpriteBatch = null
	private var camera:OrthographicCamera = null
	private var bucket:Rectangle = null
	private var raindrops:Array[Rectangle] = null
	private var lastDropTime:Long = 0

    override def create() {

    	dropImage = new Texture(Gdx.files.internal("assets/droplet.png"))
    	bucketImage = new Texture(Gdx.files.internal("assets/bucket.png"))

    	dropSound = Gdx.audio.newSound(Gdx.files.internal("assets/drop.wav"))
    	rainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/rain.mp3"))

    	rainMusic.setLooping(true)
		rainMusic.play()

		camera = new OrthographicCamera()
		camera.setToOrtho(false, 800, 480)
		batch = new SpriteBatch

		bucket = new Rectangle
		bucket.x = 800/2 - 64/2
		bucket.y = 20
		bucket.width = 64
		bucket.height = 64

		raindrops = new Array[Rectangle]
		spawnRaindrop
    }

    private def spawnRaindrop() {

    	var raindrop:Rectangle = new Rectangle
    	raindrop.x = MathUtils.random(0, 800-64)
    	raindrop.y = 480
    	raindrop.width = 64
    	raindrop.height = 64
    	raindrops.add(raindrop)
    	lastDropTime = TimeUtils.nanoTime

    }

    override def render() {
    	Gdx.gl.glClearColor(0, 0, 0.2f, 1)
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    	camera.update

    	batch.setProjectionMatrix(camera.combined)

    	batch.begin
    	batch.draw(bucketImage, bucket.x, bucket.y)
    	for(counter <- 0 to raindrops.size - 1){
    		batch.draw(dropImage, raindrops.get(counter).x, raindrops.get(counter).y)
    	}
    	batch.end

    	if(Gdx.input.isTouched()){
    		var touchPos:Vector3 = new Vector3
    		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0)
    		camera.unproject(touchPos)
    		bucket.x = touchPos.x - 64 / 2;
    	}
    	if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime
    	if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime


    	if(bucket.x < 0) bucket.x = 0
    	if(bucket.x > 800-64) bucket.x = 800 - 64

    	if(TimeUtils.nanoTime - lastDropTime > 1000000000) spawnRaindrop

/*    	for(raindrop <- raindrops){
    		raindrop.y -= 200 * Gdx.graphics.getDeltaTime
    		if(raindrop.y + 64 < 0) raindrops.removeIndex(raindrops.indexOf(raindrop))
    		if(raindrop.overlaps(bucket)) {
    			dropSound.play()
    			raindrops.removeIndex(raindrops.indexOf(raindrop))
    		}
    	}*/

    	var i:Iterator[Rectangle] = raindrops.iterator
    	while(i.hasNext){
    		val raindrop:Rectangle = i.next
    		raindrop.y -= 200 * Gdx.graphics.getDeltaTime
    		if(raindrop.y + 64 < 0) i.remove
    		if(raindrop.overlaps(bucket)) {
    			dropSound.play()
    			i.remove
    		}
    	}
    }

    override def resize(width:Int, height:Int){

    }

    override def pause(){}

    override def resume(){}

    override def dispose() {
    	dropImage.dispose
    	bucketImage.dispose
    	dropSound.dispose
    	rainMusic.dispose
    	batch.dispose
    }
}
