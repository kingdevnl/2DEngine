package nl.kingdev.engine.gui.widgets;

import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import com.sun.org.apache.xpath.internal.operations.Mod;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.Widget;
import nl.kingdev.engine.input.Mouse;
import nl.kingdev.engine.utils.MathUtil;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static java.lang.Math.*;

public class ParticleWidget extends Widget {

    //            float x = ((particle.pos.x / 1080) * 2) - 1;
//            float y = ((particle.pos.y / 720) * 2) - 1;
    static Random random = new Random();

    static int range = 50;
    static int mouseRange = 100;

    private class Particle {

        Vector2f pos = new Vector2f();
        Vector2f vel;
        Vector3f color;

        float speed = 1f;


        public Particle() {

            vel = new Vector2f((float) (Math.random() * 2.0f - 1.0f),
                (float) (Math.random() * 2.0f - 1.0f));

            float x = random.nextInt(1080);
            float y = random.nextInt(720);

            pos = new Vector2f(x, y);

            color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        }

        void connect(float x, float y) {
            Vector2d mousePos = Mouse.getMousePos();
            if (getDistanceTo((int) mousePos.x, (int) mousePos.y) < mouseRange) {
                drawLine(pos.x, pos.y, x, y, new Vector3f(color.x, color.y+50, color.z));

            } else {
                drawLine(pos.x, pos.y, x, y, new Vector3f(1,1,1));
            }
        }

        float getX() {
            return pos.x;
        }

        float getY() {
            return pos.y;
        }

        void tick() {
            pos.x += vel.x * speed;
            pos.y += vel.y * speed;

            if (pos.x() > Application.instance.display.getWidth()) {
                pos.x = (0);
            }
            if (pos.x() < 0) {
                pos.x = (Application.instance.display.getWidth());
            }

            if (pos.y() > Application.instance.display.getHeight()) {
                pos.y = (0);
            }
            if (pos.y < 0) {
                pos.y = (Application.instance.display.getHeight());
            }
        }

        public float getDistanceTo(Particle particle1) {
            return getDistanceTo(particle1.pos.x, particle1.pos.y);
        }

        public float getDistanceTo(float x, float y) {
            return (float) MathUtil.distance(getX(), getY(), x, y);
        }
    }

    private List<Particle> particleList = new ArrayList<>();

    public ParticleWidget(int num) {
        for (int i = 0; i < num; i++) {
            particleList.add(new Particle());
        }
    }

    public static float map(float val) {
        return ((val / 1080) * 2) - 1;
    }

    @Override
    public void render() {
        glPushMatrix();
        glOrtho(0, 1080, 720, 0, -1, 1);
        for (Particle particle : particleList) {
            GL11.glColor4f(particle.color.x, particle.color.y, particle.color.z, 0);
            GL11.glPointSize(10.0f);
            drawCircle(particle.pos.x, particle.pos.y, 4, particle.color);

            particleList.stream()
                .filter(
                    part -> (part.getX() > particle.getX() && part.getX() - particle.getX() < range
                        && particle.getX() - part.getX() < range)
                        && (part.getY() > particle.getY() && part.getY() - particle.getY() < range
                        || particle.getY() > part.getY() && particle.getY() - part.getY() < range))
                .forEach(connectable -> particle.connect(connectable.getX(), connectable.getY()));

        }

        glPopMatrix();

    }


    public static void drawCircle(float xx, float yy, float radius, Vector3f color) {
        int sections = 50;
        double dAngle = 2 * Math.PI / sections;
        float x, y;

        glPushMatrix();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glBegin(GL_TRIANGLE_FAN);

        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            glColor4f(color.x, color.y, color.z, 1);
            glVertex2f(xx + x, yy + y);
        }
        glColor4f(color.x, color.y, color.z, 1);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glPopMatrix();
    }

    private void drawLine(float x, float y, float x1, float y1, Vector3f color) {
        GL11.glColor4f(color.x, color.y, color.z, 0);

        GL11.glLineWidth(0.1f);
        GL11.glBegin(GL11.GL_LINES);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();

    }

    @Override
    public void tick() {
        for (Particle particle : particleList) {
            particle.tick();
        }
    }
}
