package nl.kingdev.engine.state.states;

import nl.kingdev.engine.gui.GuiScreen;
import nl.kingdev.engine.state.GameState;

public class GuiState extends GameState {

    private GuiScreen currentScreen;

    @Override
    public void render() {
        getCurrentScreen().render();
    }

    @Override
    public void tick() {
        getCurrentScreen().tick();
    }

    public GuiState(GuiScreen currentScreen) {
        this.currentScreen = currentScreen;
        setStateName("guiState [" + currentScreen.getClass().getSimpleName() + "]");
    }


    public <T extends GuiScreen> T getCurrentScreen() {
        return (T) currentScreen;
    }


    @Override
    public void onClick(double x, double y, int btn) {
        this.currentScreen.onClick(x, y, btn);
    }
}
