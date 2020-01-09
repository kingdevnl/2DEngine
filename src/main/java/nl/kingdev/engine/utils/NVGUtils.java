package nl.kingdev.engine.utils;

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
        return rgba(r,g,b,a,NVGColor.create());
    }
    public static NVGColor rgb(int r, int g, int b) {
        return rgba(r,g,b,255,NVGColor.create());
    }


}
