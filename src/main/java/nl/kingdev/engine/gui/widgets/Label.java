package nl.kingdev.engine.gui.widgets;

import nl.kingdev.engine.gui.Widget;
import nl.kingdev.engine.utils.FontUtil;

public class Label extends Widget {

    private String label;

    public String font = "Robotto";
    public int fontSize = 25;

    public Label(int x, int y, String label) {
        setX(x);
        setY(y);
        this.label = label;
    }

    public Label(int x, int y, String label, String font, int fontSize) {
        setX(x);
        setY(y);

        this.label = label;
        this.font = font;
        this.fontSize = fontSize;

    }

    @Override
    public void render() {
        FontUtil.renderText(getX(), getY(), font, label, fontSize);
    }
}
