package nl.kingdev.engine.gui.widgets;

import static nl.kingdev.engine.utils.NVGUtils.cpToUTF8;
import static nl.kingdev.engine.utils.NVGUtils.rgba;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.Widget;
import nl.kingdev.engine.input.Mouse;
import nl.kingdev.engine.interfaces.IButtonCallback;
import nl.kingdev.engine.utils.NVGUtils;
import org.joml.Vector2d;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryStack;


public class Button extends Widget {

    public String label;
    private int width;
    private int height = 30;
    private IButtonCallback buttonCallback;

    private Rectangle bounds;

    public static final ByteBuffer ICON_SEARCH = cpToUTF8(0x1F50D);
    public static final ByteBuffer ICON_CIRCLED_CROSS = cpToUTF8(0x2716);
    public static final ByteBuffer ICON_CHEVRON_RIGHT = cpToUTF8(0xE75E);
    public static final ByteBuffer ICON_CHECK = cpToUTF8(0x2713);
    public static final ByteBuffer ICON_LOGIN = cpToUTF8(0xE740);
    public static final ByteBuffer ICON_TRASH = cpToUTF8(0xE729);

    private ByteBuffer preicon;

    public Button(int x, int y, int width, String label, ByteBuffer preicon,
        IButtonCallback callback) {
        setX(x);
        setY(y);
        this.width = width;
        this.label = label;
        this.buttonCallback = callback;
        this.preicon = preicon;

        bounds = new Rectangle(getX() - (width / 2), getY() - (height / 2), width, height);
    }

    public void setButtonCallback(IButtonCallback buttonCallback) {
        this.buttonCallback = buttonCallback;
    }

    @Override
    public void render() {
        long vg = Application.instance.display.getVg();
        drawButton(vg, preicon, label, getX() - (width / 2), getY() - (height / 2), width,
            height, isMouseOver(Mouse.getMousePos()) ? rgba(128, 16, 8, 255, colorA)
                : rgba(100, 16, 8, 255, colorA));
    }

    static final NVGColor
        colorA = NVGColor.create(),
        colorB = NVGColor.create(),
        colorC = NVGColor.create();

    static final NVGPaint
        paintA = NVGPaint.create();

    private static void drawButton(long vg, ByteBuffer preicon, String text, float x, float y,
        float w, float h, NVGColor col) {
        NVGPaint bg = paintA;
        float cornerRadius = 4.0f;
        float tw, iw = 0;

        nvgLinearGradient(vg, x, y, x, y + h,
            rgba(255, 255, 255, NVGUtils.isBlack(col) ? 16 : 32, colorB),
            rgba(0, 0, 0, NVGUtils.isBlack(col) ? 16 : 32, colorC), bg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x + 1, y + 1, w - 2, h - 2, cornerRadius - 1);
        if (!NVGUtils.isBlack(col)) {
            nvgFillColor(vg, col);
            nvgFill(vg);
        }
        nvgFillPaint(vg, bg);
        nvgFill(vg);

        nvgBeginPath(vg);
        nvgRoundedRect(vg, x + 0.5f, y + 0.5f, w - 1, h - 1, cornerRadius - 0.5f);
        nvgStrokeColor(vg, rgba(0, 0, 0, 48, colorA));
        nvgStroke(vg);

        try (MemoryStack stack = stackPush()) {
            ByteBuffer textEncoded = stack.ASCII(text, false);

            nvgFontSize(vg, 20.0f);
            nvgFontFace(vg, "Robotto-Bold");
            tw = nvgTextBounds(vg, 0, 0, textEncoded, (FloatBuffer) null);
            if (preicon != null) {
                nvgFontSize(vg, h * 1.3f);
                nvgFontFace(vg, "entypo");
                iw = nvgTextBounds(vg, 0, 0, preicon, (FloatBuffer) null);
                iw += h * 0.15f;
            }

            if (preicon != null) {
                nvgFontSize(vg, h * 1.3f);
                nvgFontFace(vg, "entypo");
                nvgFillColor(vg, rgba(255, 255, 255, 96, colorA));
                nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
                nvgText(vg, x + w * 0.5f - tw * 0.5f - iw * 0.75f, y + h * 0.5f, preicon);
            }

            nvgFontSize(vg, 20.0f);
            nvgFontFace(vg, "Robotto-Bold");
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
            nvgFillColor(vg, rgba(0, 0, 0, 160, colorA));
            nvgText(vg, x + w * 0.5f - tw * 0.5f + iw * 0.25f, y + h * 0.5f - 1, textEncoded);
            nvgFillColor(vg, rgba(255, 255, 255, 160, colorA));
            nvgText(vg, x + w * 0.5f - tw * 0.5f + iw * 0.25f, y + h * 0.5f, textEncoded);
        }
    }

    private boolean isMouseOver(Vector2d mousePos) {
        return isMouseOver(mousePos.x, mousePos.y);
    }

    private boolean isMouseOver(double x, double y) {
        return new Rectangle((int) x, (int) y, 5, 5).intersects(bounds);
    }

    @Override
    public void onClick(double x, double y, int btn) {
        if (isMouseOver(x, y)) {
            if (buttonCallback != null) {
                buttonCallback.click(this);
            }
        }
    }



}
