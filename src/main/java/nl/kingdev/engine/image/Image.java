package nl.kingdev.engine.image;

import static org.lwjgl.nanovg.NanoVG.*;

import nl.kingdev.engine.app.Application;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class Image {
    private int id;
    private int width, height;

    public Image(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void drawRect(float x, float y) {
        long vg= Application.instance.display.getVg();
        nvgSave(vg);
        nvgBeginPath(vg);
        nvgTranslate(vg, x,y);
        NVGPaint paint = NVGPaint.create();
        nvgImagePattern(vg, 0, 0, getWidth(), getHeight(), (float) (10f * Math.PI), getId(), 1, paint);
        nvgRect(vg, 0, 0, getWidth(), getHeight());
        nvgFillPaint(vg, paint);
        nvgFill(vg);
        nvgRestore(vg);
    }

    public void destroy() {
        NanoVG.nnvgDeleteImage(Application.instance.display.getVg(), getId());
    }
 }

