package nl.kingdev.engine.state;

import nl.kingdev.engine.interfaces.*;

public abstract class GameState implements IInitializable, ITickable, IRenderable, IClickable,
    IKeyCallback {

    private String stateName;


    @Override
    public void init() {

    }

    @Override
    public void render() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void onKey(int key, int action) {

    }

    @Override
    public void onClick(double x, double y, int btn) { }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }
}
