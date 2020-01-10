package nl.kingdev.engine.gui;

import nl.kingdev.engine.interfaces.IClickable;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;

public abstract class Widget implements IRenderable, ITickable, IClickable {

    private int x, y;


    @Override
    public void render() {
    }

    @Override
    public void tick() {
    }

    @Override
    public void onClick(double x, double y, int btn) {    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
