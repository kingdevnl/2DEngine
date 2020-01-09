package nl.kingdev.engine.app;

import nl.kingdev.engine.display.Display;
import nl.kingdev.engine.state.GameState;

public class Application {
    public Display display;
    protected GameState currentState;
    protected GameState prevState;
    public static Application instance;


    public void boot() {

    }

    public <T extends GameState> T  getState() {
        return (T)currentState;
    }


    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        prevState = this.currentState;
        this.currentState = currentState;
        this.currentState.init();
    }
    public void popState() {
        this.currentState = this.prevState;
    }
}
