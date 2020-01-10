package nl.kingdev.testing.bird;

import static org.lwjgl.nanovg.NanoVG.*;

import java.io.FileNotFoundException;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.image.Image;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;
import nl.kingdev.engine.utils.ImageLoader;
import nl.kingdev.engine.utils.Timer;
import org.lwjgl.nanovg.NVGPaint;

public class Bird implements IRenderable, ITickable {

    private float x, y;
    private float velocity = 0;
    private float gravity = 0.1f;
    private float lift = -3f;
    static Image birdImg = null;
    private Timer timer = new Timer();

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;

        if (birdImg == null) {
            try {
                birdImg = ImageLoader.loadImage("./res/textures/bird.png");
                birdImg.setWidth(birdImg.getWidth() / 2);
                birdImg.setHeight(birdImg.getHeight() / 2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isDeath() {

        if (this.y > Application.instance.display.getHeight() || this.y < 0) {
            return true;
        }

        return false;
    }


    @Override
    public void render() {
        long vg = Application.instance.display.getVg();

        birdImg.drawRect(x,y);
    }

    @Override
    public void tick() {

        if (timer.hasTimePassed(10)) {
            this.velocity += this.gravity;
            timer.reset();
        }

        this.y += this.velocity;
    }

    public void flap() {
        this.velocity += this.lift;

    }
}
