package com.main;

import com.GameState.GameStateManager;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

/**Main class of the game where all game components come together*/
public class Main {

    //Window dimensions(To be used for all levels so they're public)
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int SCALE = 2;

    //Checks if application is running
    private boolean running;
    private int fps = 100;

    //Main screen for game
    RenderWindow window = new RenderWindow(new VideoMode(WIDTH * SCALE, HEIGHT * SCALE), "The Game");

    private Texture image;
    private Sprite sprite;

    //game state manager
    private GameStateManager gsm;

    /**Main class*/
    public Main() {
        init();
        while (running) {
            update();
            draw();
        }
    }

    /**Initializes the game*/
    private void init() {
        running = true;
        gsm = new GameStateManager(window);
        window.clear();
        window.setFramerateLimit(fps);
    }

    /**Updates game window based on user input*/
    private void update() {
        for (Event event : window.pollEvents()) {

            if (event.type == Event.Type.CLOSED) {

                running = false;
                window.close();

            }else if(event.type == Event.Type.KEY_PRESSED){

                KeyEvent keyEvent = event.asKeyEvent();
                keyPressed(keyEvent);

            }else if (event.type == Event.Type.KEY_RELEASED){

                KeyEvent keyEvent1 = event.asKeyEvent();
                keyReleased(keyEvent1);

            }
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
            running = false;

            window.close();
        }
        gsm.update();
        window.display();
    }

    /**Draws contents of window on gsm*/
    private void draw() {
        gsm.draw();
    }

    /**Passes user input into the gameStateManager*/
    public void keyPressed(KeyEvent keyEvent){
        gsm.keyPressed(keyEvent);
    }

    public void keyReleased(KeyEvent keyEvent){
        gsm.keyReleased(keyEvent);
    }

    /**Starts the application*/
    public static void main(String[] args) {
        Main main = new Main();
    }
}
