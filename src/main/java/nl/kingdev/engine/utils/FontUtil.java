package nl.kingdev.engine.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.display.Display;

import static org.lwjgl.nanovg.NanoVG.*;

public class FontUtil {

    public static void renderText(int x, int y, String font, String text, float fontSize) {
        long vg = Application.instance.display.getVg();
        nvgFontSize(vg, fontSize);
        nvgFontFace(vg, font);
        nvgTextAlign(vg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
        nvgFillColor(vg, NVGUtils.rgb(255, 0, 0));
        nvgText(vg, x, y, text);
    }
    public static void loadFont(String resName, String name, int size) {
        try {
            ByteBuffer buffer = IOUtil.ioResourceToByteBuffer(resName, size);
            int font = nvgCreateFontMem(Application.instance.display.getVg(), name, buffer, 0);
            if (font == -1) {
                System.err.println("Error creating font!");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
