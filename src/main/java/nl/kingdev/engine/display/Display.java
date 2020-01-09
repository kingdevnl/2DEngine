package nl.kingdev.engine.display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.GL;

public class Display {

    private long handle;
    private int width, height;

    private long vg;

    public Display(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private Display create(String title) {
        if (!glfwInit()) {
            System.err.println("Failed to init GLFW!");
            System.exit(1);
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        handle = glfwCreateWindow(width, height, title, 0, 0);
        glfwSwapInterval(1);
        glfwMakeContextCurrent(handle);
        GL.createCapabilities();

        return this;
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(handle);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(handle);
    }

    public void setVisible(boolean show) {
        if (show) {
            glfwShowWindow(handle);
        } else {
            glfwHideWindow(handle);
        }
    }

    public long getVg() {
        if (vg == 0) {
            vg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
        }
        return vg;
    }

    public static Display create(int width, int height, String title) {
        Display display = new Display(width, height);
        return display.create(title);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getHandle() {
        return handle;
    }
}
