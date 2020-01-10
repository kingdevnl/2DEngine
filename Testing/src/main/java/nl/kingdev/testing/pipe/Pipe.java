package nl.kingdev.testing.pipe;

import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.display.Display;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;
import nl.kingdev.engine.utils.MathUtil;
import nl.kingdev.engine.utils.NVGUtils;

import static org.lwjgl.nanovg.NanoVG.*;

public class Pipe implements IRenderable, ITickable {

    private Display display = Application.instance.display;
    private int spacing = 125;
    private int centerY = MathUtil.randomInt(spacing, display.getHeight() - spacing);
    private int top = centerY - spacing / 2;
    private int bottom = display.getHeight() - (centerY + spacing / 2);

    public static int w = 80;
    private int x = display.getWidth();

    private float speed = 1.5f;


    public Pipe() {

    }

    @Override
    public void render() {
        drawRect(x, 0, w, top);
        drawRect(x, display.getHeight() - bottom, w, bottom);
    }

    private void drawRect(int x, int y, int w, int h) {
        long vg = display.getVg();

        nvgSave(vg);

        nvgTranslate(vg,x,y);
        nvgBeginPath(vg);
        nvgRect(vg, 0, 0, w, h);
        nvgFillColor(vg, NVGUtils.rgb(255, 255, 255));
        nvgFill(vg);

        nvgRestore(vg);
    }

    @Override
    public void tick() {
        this.x -= this.speed;
    }

    public boolean outOfBounds() {
        return this.x < -this.w;
    }
}
