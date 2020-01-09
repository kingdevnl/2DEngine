package nl.kingdev.engine.gui.widgets;

import java.awt.Rectangle;
import java.nio.ByteBuffer;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.Widget;
import org.joml.Vector2d;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static java.lang.Math.*;
import static nl.kingdev.engine.utils.NVGUtils.*;

public class Checkbox extends Widget {

    private boolean checked;
    private int width, height;
    private String label;
    private static final ByteBuffer CHECK_ICON = cpToUTF8(0x2713);
    private Rectangle bounds;

    private NVGColor color;
    public Checkbox(int x, int y, int width, int height, String label, NVGColor color) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        this.label = label;
        this.color = color;
        bounds = new Rectangle(getX(),getY(), width, height);

    }


    @Override
    public void render() {
        drawCheckbox();
    }

    private void drawCheckbox() {
        long vg = Application.instance.display.getVg();
        NVGPaint bg = NVGPaint.create();
        nvgFontSize(vg, 18);
        nvgFontFace(vg, "Robotto");
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);

        nvgFillColor(vg, color);
        nvgBeginPath(vg);
        nvgRect(vg, getX(), getY(), width, height);
        nvgFill(vg);

        nvgFontSize(vg, (float) (width));

        nvgBeginPath(vg);
        nvgText(vg, (float) (getX() + (width) + (width/4.5)), getY() + (height / 2), label);
        nvgFill(vg);

        nvgBeginPath(vg);
        nvgFontFace(vg, "entypo");
        nvgFontSize(vg, height * 1.5f);
        if(checked) {
            nvgFillColor(vg, rgb(0, 255, 0));
        } else {
            nvgFillColor(vg, rgba(255, 0, 0, 255));

        }


        nvgText(vg, (float) (getX() + (width/4.5)), getY() + (height / 2), CHECK_ICON);
        nvgFill(vg);

//        nvgFill(vg);
////        nvgBeginPath(vg);
////        nvgText(vg, getX()+ width, getY(), label);
////        nvgFill(vg);

//
//
//        nvgFontSize(vg, height * 2);
//        nvgFontFace(vg, "entypo");
//        nvgFillColor(vg, rgba(255, 255, 255, 96));
//        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
//        nvgText(vg, getX() , getY(), CHECK_ICON);

    }

    @Override
    public void onClick(double x, double y, int btn) {
        super.onClick(x, y, btn);
        if(isMouseOver(x,y)) {
            if(btn == 0) {
                checked = !checked;
            }
        }
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
    private boolean isMouseOver(Vector2d mousePos) {
        return isMouseOver(mousePos.x, mousePos.y);
    }

    private boolean isMouseOver(double x, double y) {
        return new Rectangle((int) x, (int) y, 5, 5).intersects(bounds);
    }


}
