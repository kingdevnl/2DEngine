package nl.kingdev.testing;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.display.Display;
import nl.kingdev.engine.input.Mouse;
import nl.kingdev.engine.interfaces.IRenderable;
import nl.kingdev.engine.interfaces.ITickable;
import nl.kingdev.engine.state.states.GuiState;
import nl.kingdev.engine.utils.FontUtil;
import nl.kingdev.testing.screens.HomeScreen;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

public class Sandbox extends Application implements IRenderable, ITickable {

    public static int WIDTH = 1080, HEIGHT = 720;

    @Override
    public void boot() {
        instance = this;
        this.display = Display.create(WIDTH, HEIGHT, "Sandbox");
        display.setVisible(true);
        setCurrentState(new GuiState(new HomeScreen()));
//        setCurrentState(new TestState());
        FontUtil.loadFont("/Robotto.ttf", "Robotto", 150 * 1024);
        FontUtil.loadFont("/entypo.ttf", "entypo", 40 * 1024);
        FontUtil.loadFont("/Roboto-Regular.ttf", "Robotto-Regular", 150 * 1024);
        FontUtil.loadFont("/Roboto-Bold.ttf", "Robotto-Bold", 150 * 1024);
        FontUtil.loadFont("/NotoEmoji-Regular.ttf", "NotoEmoji-Regular", 450 * 1024);

        GLFW.glfwSetMouseButtonCallback(display.getHandle(), (window, button, action, mods) -> {

            if (action == GLFW.GLFW_PRESS) {
                Vector2d mousePos = Mouse.getMousePos();
                currentState.onClick(mousePos.x, mousePos.y, button);
            }
        });
        while (!display.isCloseRequested()) {
            display.clear();
            tick();
            render();
            display.update();

        }
    }


    @Override
    public void render() {
        long vg = Application.instance.display.getVg();

        nvgBeginFrame(vg, Application.instance.display.getWidth(),
            Application.instance.display.getHeight(), 1);
        getCurrentState().render();
        nvgEndFrame(vg);
    }

    @Override
    public void tick() {
        getCurrentState().tick();
    }
}
