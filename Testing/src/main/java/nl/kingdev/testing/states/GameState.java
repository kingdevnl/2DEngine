package nl.kingdev.testing.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.state.states.GuiState;
import nl.kingdev.engine.utils.FontUtil;
import nl.kingdev.testing.background.Background;
import nl.kingdev.testing.bird.Bird;
import nl.kingdev.testing.screens.HomeScreen;
import org.lwjgl.glfw.GLFW;

public class GameState extends nl.kingdev.engine.state.GameState {



    public GameState() {
        setStateName("GameState");
    }


    private List<Background> backgrounds = new ArrayList<>();
    private Bird bird = null;

    @Override
    public void init() {
        super.init();
        long vg = Application.instance.display.getVg();

        backgrounds.add(new Background(0));

        for (int i = 0; i < 5; i++) {
            backgrounds.add(new Background(Background.bgImg.getWidth()*i));
        }

        bird = new Bird(100, Application.instance.display.getHeight()/2);
    }


    @Override
    public void render() {
        super.render();
        long vg = Application.instance.display.getVg();
        backgrounds.forEach(Background::render);

        if(!bird.isDeath()) {
            bird.render();
        } else {
            FontUtil.renderText(Application.instance.display.getWidth()/2, 100, "Robotto", "Game over.", 25);
        }
    }

    @Override
    public void tick() {
        if(!bird.isDeath()) {
            backgrounds.forEach(Background::tick);

            bird.tick();
        }

    }

    @Override
    public void onKey(int key, int action) {
        if(action == GLFW.GLFW_PRESS) {
            if(key == GLFW.GLFW_KEY_SPACE) {
                if(!bird.isDeath()) {
                bird.flap();
                }
            }
            if(key == GLFW.GLFW_KEY_ESCAPE) {
                Application.instance.setCurrentState(new GameState(), true);
            }
            if(key == GLFW.GLFW_KEY_DELETE) {
                Application.instance.setCurrentState(new GuiState(new HomeScreen()), true);
            }
        }

    }
}
