package com.GameState;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.KeyEvent;


/**Provides necessary methods to impolement the
 * game state manager*/
public abstract class GameState {

    protected GameStateManager gsm;

    public abstract void init();

    public abstract void update();

    public abstract void draw(RenderWindow window);

    public abstract void keyPressed(KeyEvent keyEvent);

    public abstract void keyReleased(KeyEvent keyEvent);
}