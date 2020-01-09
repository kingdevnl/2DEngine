package nl.kingdev.engine.utils;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.ARBTimerQuery.GL_TIME_ELAPSED;
import static org.lwjgl.opengl.ARBTimerQuery.glGetQueryObjectui64v;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;

/**
 * Graph
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/01/2020 - 01:09 pm
 */
public class Graph {

    public static final int
        GRAPH_RENDER_FPS = 0;
    public static final int GRAPH_RENDER_MS = 1;
    static final int GRAPH_RENDER_PERCENT = 2;

    static final NVGColor
        colorA = NVGColor.create();

    private static final int GRAPH_HISTORY_COUNT = 100;

    private static final int GPU_QUERY_COUNT = 5;

    public static class PerfGraph {

        int style;
        ByteBuffer name = BufferUtils.createByteBuffer(32);
        float[] values = new float[GRAPH_HISTORY_COUNT];
        int head;
    }

    public static class GPUtimer {

        public boolean supported;
        int cur, ret;
        IntBuffer queries = BufferUtils.createIntBuffer(GPU_QUERY_COUNT);
    }

    public static void initGPUTimer(GPUtimer timer) {
        //memset(timer, 0, sizeof(*timer));
        timer.supported = GL.getCapabilities().GL_ARB_timer_query;
        timer.cur = 0;
        timer.ret = 0;
        BufferUtils.zeroBuffer(timer.queries);

        if (timer.supported) {
            glGenQueries(timer.queries);
        }
    }

    static void startGPUTimer(GPUtimer timer) {
        if (!timer.supported) {
            return;
        }
        glBeginQuery(GL_TIME_ELAPSED, timer.queries.get(timer.cur % GPU_QUERY_COUNT));
        timer.cur++;
    }

    public static int stopGPUTimer(GPUtimer timer, FloatBuffer times, int maxTimes) {
        int n = 0;
        if (!timer.supported) {
            return 0;
        }

        glEndQuery(GL_TIME_ELAPSED);

        try (MemoryStack stack = stackPush()) {
            IntBuffer available = stack.ints(1);
            while (available.get(0) != 0 && timer.ret <= timer.cur) {
                // check for results if there are any
                GL15.glGetQueryObjectiv(timer.queries.get(timer.ret % GPU_QUERY_COUNT),
                    GL_QUERY_RESULT_AVAILABLE, available);
                if (available.get(0) != 0) {
                    LongBuffer timeElapsed = stack.mallocLong(1);
                    glGetQueryObjectui64v(timer.queries.get(timer.ret % GPU_QUERY_COUNT),
                        GL_QUERY_RESULT, timeElapsed);
                    timer.ret++;
                    if (n < maxTimes) {
                        times.put(n, (float) ((double) timeElapsed.get(0) * 1e-9));
                        n++;
                    }
                }
            }
        }
        return n;
    }

 public   static void initGraph(PerfGraph fps, int style, String name) {
        fps.style = style;
        memUTF8(name, false, fps.name);
        Arrays.fill(fps.values, 0);
        fps.head = 0;
    }

  public  static void updateGraph(PerfGraph fps, float frameTime) {
        fps.head = (fps.head + 1) % GRAPH_HISTORY_COUNT;
        fps.values[fps.head] = frameTime;
    }

    static float getGraphAverage(PerfGraph fps) {
        float avg = 0;
        for (int i = 0; i < GRAPH_HISTORY_COUNT; i++) {
            avg += fps.values[i];
        }
        return avg / (float) GRAPH_HISTORY_COUNT;
    }

  public  static void renderGraph(long vg, float x, float y, PerfGraph fps) {
        float avg = getGraphAverage(fps);

        int w = 200;
        int h = 35;

        nvgBeginPath(vg);
        nvgRect(vg, x, y, w, h);
        nvgFillColor(vg, rgba(0, 0, 0, 128, colorA));
        nvgFill(vg);

        nvgBeginPath(vg);
        nvgMoveTo(vg, x, y + h);
        if (fps.style == GRAPH_RENDER_FPS) {
            for (int i = 0; i < GRAPH_HISTORY_COUNT; i++) {
                float v = 1.0f / (0.00001f + fps.values[(fps.head + i) % GRAPH_HISTORY_COUNT]);
                float vx, vy;
                if (v > 1000.0f) {
                    v = 1000.0f;
                }
                vx = x + ((float) i / (GRAPH_HISTORY_COUNT - 1)) * w;
                vy = y + h - ((v / 1000.0f) * h);
                nvgLineTo(vg, vx, vy);
            }
        } else if (fps.style == GRAPH_RENDER_PERCENT) {
            for (int i = 0; i < GRAPH_HISTORY_COUNT; i++) {
                float v = fps.values[(fps.head + i) % GRAPH_HISTORY_COUNT] * 1.0f;
                float vx, vy;
                if (v > 100.0f) {
                    v = 100.0f;
                }
                vx = x + ((float) i / (GRAPH_HISTORY_COUNT - 1)) * w;
                vy = y + h - ((v / 100.0f) * h);
                nvgLineTo(vg, vx, vy);
            }
        } else {
            for (int i = 0; i < GRAPH_HISTORY_COUNT; i++) {
                float v = fps.values[(fps.head + i) % GRAPH_HISTORY_COUNT] * 1000.0f;
                float vx, vy;
                if (v > 4.0f) {
                    v = 4.0f;
                }
                vx = x + ((float) i / (GRAPH_HISTORY_COUNT - 1)) * w;
                vy = y + h - ((v / 4.0f) * h);
                nvgLineTo(vg, vx, vy);
            }
        }
        nvgLineTo(vg, x + w, y + h);
        nvgFillColor(vg, rgba(255, 192, 0, 128, colorA));
        nvgFill(vg);

        nvgFontFace(vg, "Robotto");

        if (fps.name.get(0) != '\0') {
            nvgFontSize(vg, 14.0f);
            nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(240, 240, 240, 192, colorA));
            nvgText(vg, x + 3, y + 1, fps.name);
        }

        if (fps.style == GRAPH_RENDER_FPS) {
            nvgFontSize(vg, 18.0f);
            nvgTextAlign(vg, NVG_ALIGN_RIGHT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(240, 240, 240, 255, colorA));
            nvgText(vg, x + w - 3, y + 1, String.format("%.2f FPS", 1.0f / avg));

            nvgFontSize(vg, 15.0f);
            nvgTextAlign(vg, NVG_ALIGN_RIGHT | NVG_ALIGN_BOTTOM);
            nvgFillColor(vg, rgba(240, 240, 240, 160, colorA));
            nvgText(vg, x + w - 3, y + h - 1, String.format("%.2f ms", avg * 1000.0f));
        } else if (fps.style == GRAPH_RENDER_PERCENT) {
            nvgFontSize(vg, 18.0f);
            nvgTextAlign(vg, NVG_ALIGN_RIGHT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(240, 240, 240, 255, colorA));
            nvgText(vg, x + w - 3, y + 1, String.format("%.1f %%", avg * 1.0f));
        } else {
            nvgFontSize(vg, 18.0f);
            nvgTextAlign(vg, NVG_ALIGN_RIGHT | NVG_ALIGN_TOP);
            nvgFillColor(vg, rgba(240, 240, 240, 255, colorA));
            nvgText(vg, x + w - 3, y + 1, String.format("%.2f ms", avg * 1000.0f));
        }
    }


    static NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        return color;
    }

}