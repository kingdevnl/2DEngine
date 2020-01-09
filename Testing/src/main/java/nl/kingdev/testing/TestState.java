package nl.kingdev.testing;

import nl.kingdev.engine.state.GameState;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class TestState extends GameState {

    int w = 1080;
    int h = 720;

    @Override
    public void render() {

        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, w, h, 0, -1, 1);

        GL11.glColor4f(1,1,1, 0);

        glBegin(GL_POINTS);
        glVertex2f(w/2,h/2);
        glEnd();
        glPopMatrix();

    }
}
