package com.GameState;

import com.level1.Level1State;
import com.level2.Level2State;
import com.level3.Level3State;
import com.level4.Level4State;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

/**
 * This class Manages game states (Levels, Menu etc.), it also
 * contains methods that draw or updata the whole level.
 */
public class GameStateManager {

    /**An array to stopre all the game levels/pages*/
    private ArrayList<GameState> gameStates;

    private int currentState;
    private RenderWindow window;

    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int LEVEL2STATE = 2;
    public static final int HELPMENUSTATE = 3;
    public static final int LEVEL3STATE = 4;
    public static final int LEVEL4STATE = 5;
    public static final int GAMEOVERSTATE = 6;
    public static final int WINNERSTATE = 7;


    /**
     * The constructor of GameStateManager, it will also initialize 
     * gameStates and add created level design to it.
     * @param window RenderWindow object use to draw
     */
    public GameStateManager(RenderWindow window)
    {
        this.window = window;
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
        gameStates.add(new Level2State(this));
        gameStates.add(new HelpMenuState(this));
        gameStates.add(new Level3State(this));
        gameStates.add(new Level4State(this));
        gameStates.add(new GameOverState(this));
        gameStates.add(new WinnerState(this));
    }

    /**
     * Set the state of the game in current condition
     * @param state The state id number given, called when each level is finished.
     */
    public void setState(int state)
    {
        currentState = state;
        gameStates.get(currentState).init();
    }

    /**
     * Update the current levelstate
     */
    public void update()
    {
        gameStates.get(currentState).update();
    }

    /**
     * Draws the current game state onto the window
     */
    public void draw()
    {
        gameStates.get(currentState).draw(window);
    }

    /**
     * Check if a key was pressed during the current gameState
     * @param keyEvent A required Keyevent object
     */
    public void keyPressed(KeyEvent keyEvent){
        gameStates.get(currentState).keyPressed(keyEvent);
    }

    /**
     * Check if a key was released during the current gameState
     * @param keyEvent A required Keyevent object
     */
    public void keyReleased(KeyEvent keyEvent){
        gameStates.get(currentState).keyReleased(keyEvent);
    }
}
