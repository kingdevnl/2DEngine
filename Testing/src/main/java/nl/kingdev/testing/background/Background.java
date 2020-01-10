package nl.kingdev.testing.background;

import java.io.FileNotFoundException;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.image.Image;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;
import nl.kingdev.engine.utils.ImageLoader;

public class Background implements IRenderable, ITickable {

    private int xScroll;
    private int xScrollStart;

    public static Image bgImg = null;

    public Background(int xScrollStart) {
        this.xScrollStart = xScrollStart;
        this.xScroll = xScrollStart;
        if (bgImg == null) {
            try {
                bgImg = ImageLoader.loadImage("./res/textures/bg.jpeg");
                bgImg.setHeight(Application.instance.display.getHeight());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render() {
        bgImg.drawRect(xScroll, 0);
    }

    @Override
    public void tick() {

        xScroll--;

        if(xScroll < -bgImg.getWidth()) {
            xScroll = Application.instance.display.getWidth();
        }

    }
}
