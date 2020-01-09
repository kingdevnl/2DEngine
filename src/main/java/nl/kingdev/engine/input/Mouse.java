package nl.kingdev.engine.input;

import java.nio.DoubleBuffer;
import nl.kingdev.engine.app.Application;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class Mouse {

    public static Vector2d getMousePos() {
        DoubleBuffer xPos = MemoryUtil.memAllocDouble(1);
        DoubleBuffer yPos = MemoryUtil.memAllocDouble(1);
        GLFW.glfwGetCursorPos(Application.instance.display.getHandle(), xPos, yPos);
        Vector2d vector2d = new Vector2d(xPos.get(), yPos.get());

        MemoryUtil.memFree(xPos);
        MemoryUtil.memFree(yPos);

        return vector2d;
    }

}
