package nl.kingdev.engine.gui;

import java.util.ArrayList;
import java.util.List;
import nl.kingdev.engine.interfaces.IClickable;
import nl.kingdev.engine.interfaces.IInitializable;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;

public abstract class GuiScreen implements ITickable, IRenderable, IInitializable, IClickable {


    public List<Widget> widgets = new ArrayList<>();

    public GuiScreen() {
        init();
    }

    public void addWidget(Widget widget) {
        widgets.add(widget);
    }

    @Override
    public void render() {
        widgets.forEach(Widget::render);
    }

    @Override
    public void tick() {
        widgets.forEach(Widget::tick);
    }

    @Override
    public void onClick(double x, double y, int btn) {
        widgets.forEach(widget -> widget.onClick(x, y, btn));
    }
}
