package nl.kingdev.engine.gui.widgets;

import static nl.kingdev.engine.utils.FontUtil.renderText;
import static org.lwjgl.nanovg.NanoVG.*;

import java.awt.Rectangle;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.Widget;
import nl.kingdev.engine.input.Mouse;
import nl.kingdev.engine.interfaces.IButtonCallback;
import nl.kingdev.engine.utils.NVGUtils;
import org.joml.Vector2d;


public class Button extends Widget {

    public String label;
    private int width;
    private int height = 30;
    private IButtonCallback buttonCallback;

    private Rectangle bounds;

    public Button(int x, int y, int width, String label, IButtonCallback callback) {
        setX(x);
        setY(y);
        this.width = width;
        this.label = label;
        this.buttonCallback = callback;

        bounds = new Rectangle(getX() - (width / 2), getY() - (height / 2), width, height);
    }

    public void setButtonCallback(IButtonCallback buttonCallback) {
        this.buttonCallback = buttonCallback;
    }

    @Override
    public void render() {
        long vg = Application.instance.display.getVg();
        nvgBeginPath(vg);
        nvgRect(vg, getX() - (width / 2), getY() - (height / 2), width, height);
        nvgFillColor(vg, NVGUtils.rgba(255, 192, 0, 255));
        if (isMouseOver(Mouse.getMousePos())) {
            nvgFillColor(vg, NVGUtils.rgba(140, 113, 182, 255));

            nvgRect(vg, getX() - (width / 2), getY() - (height / 2), width, height);
            nvgStrokeColor(vg, NVGUtils.rgba(0, 0, 255, 255));
            nvgStroke(vg);
        }

        nvgFill(vg);
        renderText(getX(), getY(), "Robotto", label, 25);

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
