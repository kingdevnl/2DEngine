package nl.kingdev.testing.screens;

import static nl.kingdev.engine.utils.Graph.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.nio.FloatBuffer;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.GuiScreen;
import nl.kingdev.engine.gui.widgets.Button;
import nl.kingdev.engine.gui.widgets.Label;
import nl.kingdev.engine.gui.widgets.ParticleWidget;
import nl.kingdev.engine.utils.Graph;
import nl.kingdev.engine.utils.Graph.GPUtimer;
import nl.kingdev.engine.utils.Graph.PerfGraph;
import nl.kingdev.testing.Sandbox;
import nl.kingdev.testing.states.GameState;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class HomeScreen extends GuiScreen {
   static GPUtimer gpuTimer = new GPUtimer();

    static  PerfGraph fps = new PerfGraph();
    static  PerfGraph cpuGraph = new PerfGraph();
    static PerfGraph gpuGraph = new PerfGraph();

    static final FloatBuffer gpuTimes = BufferUtils.createFloatBuffer(3);

    @Override
    public void init() {
        addWidget(new ParticleWidget(110));
        addWidget(new Label(Sandbox.WIDTH / 2, 80, "Sandbox", "Robotto", 200));
        addWidget(new Button(Sandbox.WIDTH / 2, (Sandbox.HEIGHT / 2) - 60, 200, "Play",Button.ICON_CHECK,this::onClickPlay));
        addWidget(new Button(Sandbox.WIDTH / 2, (Sandbox.HEIGHT / 2) - 10, 200, "Exit",Button.ICON_CIRCLED_CROSS, button -> System.exit(0)));


        initGraph(fps, GRAPH_RENDER_FPS, "Frame Time");
        initGraph(cpuGraph, GRAPH_RENDER_MS, "CPU Time");
        initGraph(gpuGraph, GRAPH_RENDER_MS, "GPU Time");
        initGPUTimer(gpuTimer);

    }

    double prevt = glfwGetTime();
    @Override
    public void render() {
        super.render();
        double t, dt;

        t = glfwGetTime();
        dt = t - prevt;
        prevt = t;

        updateGraph(fps, (float) dt);

        long vg = Application.instance.display.getVg();
        renderGraph(vg, 5, 5, fps);
        renderGraph(vg, 5 + 200 + 5, 5, cpuGraph);
        if (gpuTimer.supported) {
            renderGraph(vg, 5 + 200 + 5 + 200 + 5, 5, gpuGraph);
        }


        double cpuTime = glfwGetTime() - t;

        updateGraph(fps, (float) dt);
        updateGraph(cpuGraph, (float) cpuTime);

        // We may get multiple results.
        int n = stopGPUTimer(gpuTimer, gpuTimes, 3);
        for (int i = 0; i < n; i++) {
            updateGraph(gpuGraph, gpuTimes.get(i));
        }
    }

    public void onClickPlay(Button button) {
        System.out.println("Clicked on play");
        Application.instance.setCurrentState(new GameState());
    }

}
