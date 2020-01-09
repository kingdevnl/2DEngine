package nl.kingdev.testing.states;

import java.util.ArrayList;
import java.util.List;
import nl.kingdev.engine.app.Application;
import nl.kingdev.engine.gui.Widget;
import nl.kingdev.engine.gui.widgets.Button;
import nl.kingdev.engine.gui.widgets.Label;
import nl.kingdev.testing.Sandbox;

public class GameState extends nl.kingdev.engine.state.GameState {

    private List<Widget> guiWidgets = new ArrayList<>();

    public GameState() {
        setStateName("GameState");
    }

    @Override
    public void init() {
        super.init();

        guiWidgets.add(new Label(
            Sandbox.WIDTH / 2,
            Sandbox.HEIGHT / 2,
            getStateName(),
            "Robotto",
            22
        ));
        guiWidgets.add(new Button(Sandbox.WIDTH/2, Sandbox.HEIGHT/2+50,
            100, "Back",
            Button.ICON_CIRCLED_CROSS, button -> Application.instance.popState()));
    }

    @Override
    public void render() {
        super.render();

        guiWidgets.forEach(Widget::render);
    }

    @Override
    public void tick() {
        super.tick();
        guiWidgets.forEach(Widget::tick);
    }

    @Override
    public void onClick(double x, double y, int btn) {
        super.onClick(x, y, btn);

        guiWidgets.forEach(w -> w.onClick(x, y, btn));



    }
}
