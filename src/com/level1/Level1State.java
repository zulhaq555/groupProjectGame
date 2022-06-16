package com.level1;

import com.GameState.GameState;
import com.GameState.GameStateManager;
import com.TileMap.Background;
import com.TileMap.TileMap;
import com.entities.PlayerStats;
import com.entities.enemies.Enemy;
import com.entities.enemies.Skull;
import com.entities.gameObjects.Blast;
import com.entities.gameObjects.Coin;
import com.entities.player.Player;
import com.main.Main;
import com.sound.GameSound;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

public class    Level1State extends GameState {

    private TileMap tileMap;
    private Player player;
    private Background background;
    private GameSound backgroundSound;
    private ArrayList<Enemy> enemies;
    private ArrayList<Blast> blasts;
    private ArrayList<Coin> coins;
    private ArrayList<String> sounds;
    private PlayerStats playerStats;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;

        try{
            background = new Background("src/com/resources/images/level1Images/level1BG.png", 1);
            background.setVector(-0.1, 0);
        }catch (Exception e){
            e.printStackTrace();
        }

        init();

    }

    /**initializes the level and sets all the */
    @Override
    public void init() {
        tileMap = new TileMap(32);
        tileMap.loadTiles("src/com/resources/images/level1Images/level1Tileset.png");
        tileMap.loadMap("src/com/resources/images/level1Images/level1Map.txt");
        tileMap.setPosition(0, 0);

        player = new Player(tileMap);
        player.setPosition(100, 500);
        loadEnemies();
        loadCoins();
        loadSounds();

        blasts = new ArrayList<Blast>();

        playerStats = new PlayerStats(player);

    }

    public void loadCoins() {
        coins = new ArrayList<Coin>();
        addCoin(150,720);
        addCoin(422, 592);
        addCoin(878, 720);
        addCoin(1420, 720);
        addCoin(687, 496);
        addCoin(1325, 272);
        addCoin(1715, 208);
        addCoin(2283, 432);
        addCoin(1580, 432);
        addCoin(1580, 360);
        addCoin(1985, 144);
        addCoin(968, 368);
        addCoin(2050, 432);
        addCoin(2500, 464);
        addCoin(2720, 432);
        addCoin(2930, 432);
        addCoin(2790, 720);
        addCoin(3470, 720);
        addCoin(4287, 720);
        addCoin(5036, 592);
        addCoin(4861, 720);
        addCoin(5367, 496);
        addCoin(5693, 592);
        addCoin(5986, 496);
        addCoin(6400, 400);
        addCoin(6998, 720);
        addCoin(6768, 560);
        addCoin(7120, 624);
        addCoin(7434, 496);
        addCoin(7669, 592);
        addCoin(7884, 592);
    }

    public void loadSounds() {
        sounds = new ArrayList<String>();
        sounds.add("src/com/resources/sounds/level1bkgSM.wav"); //Background sound
        sounds.add("src/com/resources/sounds/walkingForest.wav"); //Walking sound
    }

    public void addCoin(double xC, double yC) {
        Coin c = new Coin(tileMap);
        c.setPosition(xC, yC);
        coins.add(c);
    }

    /**loads the enemies into the game*/
    public void loadEnemies(){
        enemies = new ArrayList<Enemy>();
        addEnemy(150,720);
        addEnemy(422, 592);
        addEnemy(878, 720);
        addEnemy(1420, 720);
        addEnemy(687, 496);
        addEnemy(1671, 208);
        addEnemy(1600, 432);
        addEnemy(2930, 432);
        addEnemy(4287, 720);
        addEnemy(4287, 720);
        addEnemy(5768, 592);
        addEnemy(5617, 592);
        addEnemy(6998, 720);
        addEnemy(7669, 592);
        addEnemy(7884, 592);

    }

    public void addEnemy(double enemyX, double enemyY){
        Skull s = new Skull(tileMap);
        s.setPosition(enemyX, enemyY);
        enemies.add(s);
    }

    @Override
    public void update() {
        player.update();
        background.update();
        tileMap.setPosition(Main.WIDTH - player.getX(), Main.HEIGHT - player.getY());

        if(enemies.size() <= 0){
            gsm.setState(GameStateManager.LEVEL2STATE);
        }

        player.checkHit(enemies);

        if(player.isDead()){
            gsm.setState(GameStateManager.GAMEOVERSTATE);
        }

        for(Coin c: coins) {
            c.update();
            c.checkHit(player);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            if(enemies.get(i).isDead()){
                blasts.add(new Blast(enemies.get(i).getX(), enemies.get(i).getY()));
                enemies.remove(i);
                i--;
            }
        }

        for (int i = 0; i < blasts.size(); i++) {
            blasts.get(i).update();
            if(blasts.get(i).isFinished()){
                blasts.remove(i);
                i--;
            }
        }

        playerStats.setEnemies(enemies.size());

    }

    @Override
    public void draw(RenderWindow window) {
        background.draw(window);
        tileMap.draw(window);
        player.draw(window);

        for(Coin c: coins)
            c.draw(window);

        for (Enemy enemy : enemies) {
            enemy.draw(window);
        }

        for(Blast blast: blasts){
            blast.setMapPosition(tileMap.getXPosition(), tileMap.getYPosition());
            blast.draw(window);
        }

        playerStats.draw(window);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.key.equals(Keyboard.Key.RIGHT)){
            player.setRight(true);
            player.setLeft(false);
            //Starts background music, when the key is pressed for the first time.
        }
        if(keyEvent.key.equals(Keyboard.Key.LEFT)){
            player.setLeft(true);
            player.setRight(false);
        }
        if(keyEvent.key.equals(Keyboard.Key.UP)){ player.setJumping(true); }
        if(keyEvent.key.equals(Keyboard.Key.DOWN)){ player.setDown(true); }
        if(keyEvent.key.equals(Keyboard.Key.SPACE)){ player.setAttack(); }
        if(keyEvent.key.equals(Keyboard.Key.C)){ player.setShooting(); }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.key.equals(Keyboard.Key.RIGHT)){ player.setRight(false); }
        if(keyEvent.key.equals(Keyboard.Key.LEFT)){ player.setLeft(false); }
        if(keyEvent.key.equals(Keyboard.Key.UP)){ player.setJumping(false); }
        if(keyEvent.key.equals(Keyboard.Key.DOWN)){ player.setDown(false); }
    }
}
