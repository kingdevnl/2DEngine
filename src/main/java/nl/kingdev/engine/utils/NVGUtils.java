package nl.kingdev.engine.utils;

import static org.lwjgl.system.MemoryUtil.memUTF8;

import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NVGColor;

public class NVGUtils {

    public static NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        return color;
    }

    public static NVGColor rgba(int r, int g, int b, int a) {
        return rgba(r, g, b, a, NVGColor.create());
    }

    public static NVGColor rgb(int r, int g, int b) {
        return rgba(r, g, b, 255, NVGColor.create());
    }

    public static boolean isBlack(NVGColor col) {
        return col.r() == 0.0f && col.g() == 0.0f && col.b() == 0.0f && col.a() == 0.0f;
    }
    public static ByteBuffer cpToUTF8(int cp) {
        return memUTF8(new String(Character.toChars(cp)), false);
    }
}
