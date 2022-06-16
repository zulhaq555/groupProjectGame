package com.GameState;

import com.TileMap.Background;
import com.sound.GameSound;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.nio.file.Paths;

/**The main menu class for the game*/
public class MenuState extends GameState{

    private Background background;
    //Records current user choice
    private int currentChoice = 0;
    private boolean moveMenu = true;

    private GameSound backgroundMusic;
    //Menu Options
    private String[] options = {"Start", "Help", "Quit"};

    //Text attributes
    private Color titleColor;
    private Font titleFont = new Font();
    private Font font = new Font();

    private Text title;
    private Text[] text = new Text[options.length];

    /**Constructor for menu class*/
    public MenuState(GameStateManager gsm)
    {
        this.gsm = gsm;
        try {

            //Sets background and font properties
            background = new Background("src/com/resources/bg.jpg", 1);
            background.setVector(-0.1, 0);
            titleColor = new Color(235, 52, 52);
            titleFont.loadFromFile(Paths.get("src/com/resources/fonts/titleFont.ttf"));

            font.loadFromFile(Paths.get("src/com/resources/fonts/fonts.ttf"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){}

    /**Updates the background of menu state*/
    public void update()
    {
        background.update();
    }

    /**Draws the Menu onto the game window*/
    public void draw(RenderWindow window)
    {
        background.draw(window);

        // draw title
        title = new Text("HERCULES GAME!!!", titleFont, 32);
        title.setColor(titleColor);
        title.setPosition(200, 200);
        window.draw(title);

        //draw menu options
        for (int i = 0; i < options.length; i++)
        {
            text[i] = new Text(options[i], font, 20);
            if (i == currentChoice)
            {
                text[i].setColor(Color.RED);
            }
            else
            {
                text[i].setColor(Color.CYAN);
            }

            text[i].setPosition(400, 300 + (i * 50));
            window.draw(text[i]);
        }
    }

    /**Detects user input and calls sets current choice accordingly*/
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.key.equals(Keyboard.Key.RETURN)) {
            select();
        }
        if (keyEvent.key.equals(Keyboard.Key.UP)) {
            currentChoice--;
            if (currentChoice == -1)
            {
                currentChoice = options.length - 1;
            }
        }
        if (keyEvent.key.equals(Keyboard.Key.DOWN)) {
            currentChoice++;
            if (currentChoice == options.length)
            {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    /**When user presses return/enter the currentChoice is checked and the
     * appropriate method is executed*/
    private void select()
    {
        if (currentChoice == 0)
        {
            gsm.setState(GameStateManager.LEVEL1STATE);
            backgroundMusic = new GameSound("src/com/resources/sounds/level1bkgSM.wav");
            backgroundMusic.playSoundLoop();
        }
        if (currentChoice == 1)
        {
            gsm.setState(GameStateManager.HELPMENUSTATE);
        }
        if (currentChoice == 2)
        {
            System.exit(0);
        }
    }
}
