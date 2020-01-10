package nl.kingdev.engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.image.Image;
import org.lwjgl.system.CallbackI.F;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class ImageLoader {


    public static Image loadImage(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Err");
            throw new FileNotFoundException("Error texture " + path + " not found.");
        }
        long vg = Application.instance.display.getVg();
        int id = nvgCreateImage(vg, path, 0);

        Image image = new Image(id);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            image.setWidth(bufferedImage.getWidth());
            image.setHeight(bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
