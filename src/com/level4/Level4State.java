package com.level4;

import com.GameState.GameState;
import com.GameState.GameStateManager;
import com.TileMap.Background;
import com.TileMap.TileMap;
import com.entities.PlayerStats;
import com.entities.enemies.Enemy;
import com.entities.enemies.GoldDemon;
import com.entities.gameObjects.Blast;
import com.entities.gameObjects.Coin;
import com.entities.player.Player;
import com.main.Main;
import com.sound.GameSound;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

public class Level4State extends GameState {

    private TileMap tileMap;
    private Player player;
    private Background background;
    private GameSound backgroundSound;
    private ArrayList<Enemy> enemies;
    private ArrayList<Blast> blasts;
    private ArrayList<Coin> coins;
    private ArrayList<String> sounds;
    private PlayerStats playerStats;

    public Level4State(GameStateManager gsm){
        this.gsm = gsm;

        try{
            background = new Background("src/com/resources/images/level4Images/level4BG.png", 1);
            background.setVector(-0.1, 0);
        }catch (Exception e){
            e.printStackTrace();
        }

        init();

        backgroundSound = new GameSound(sounds.get(0));
        backgroundSound.playSoundLoop();
    }

    /**initializes the level and sets all the */
    @Override
    public void init() {
        tileMap = new TileMap(32);
        tileMap.loadTiles("src/com/resources/images/level4Images/level4TileSet.png");
        tileMap.loadMap("src/com/resources/images/level4Images/level4Map.txt");
        tileMap.setPosition(0, 0);

        player = new Player(tileMap);
        player.setPosition(100, 500);
        loadEnemies();
        loadCoins();
        loadSounds();

        blasts = new ArrayList<Blast>();

        playerStats = new PlayerStats(player);

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

    public void loadCoins() {
        coins = new ArrayList<Coin>();
        addCoin(226, 720);
        addCoin(462, 624);
        addCoin(820, 592);
        addCoin(2619, 720);
        addCoin(2013, 720);
        addCoin(1124, 720);
        addCoin(1208, 528);
        addCoin(1664, 496);
        addCoin(1905, 560);
        addCoin(2314, 464);
        addCoin(1874, 368);
        addCoin(1586, 336);
        addCoin(1155, 400);
        addCoin(628, 240);
        addCoin(2873, 464);
        addCoin(2541, 496);
        addCoin(3337, 624);
        addCoin(3676, 432);
        addCoin(3935, 400);
        addCoin(4277, 400);
        addCoin(4804, 560);
        addCoin(4622, 720);
        addCoin(5063, 720);
        addCoin(5554, 656);
        addCoin(6179, 592);
        addCoin(6808, 656);
        addCoin(7349, 720);
        addCoin(7786, 720);
        addCoin(6622, 656);
    }

    /**loads the enemies into the game*/
    public void loadEnemies(){
        enemies = new ArrayList<Enemy>();
        addEnemy(462, 624);
        addEnemy(2619, 720);
        addEnemy(2013, 720);
        addEnemy(1388, 560);
        addEnemy(1738, 592);
        addEnemy(2314, 464);
        addEnemy(1874, 368);
        addEnemy(1155, 400);
        addEnemy(628, 240);
        addEnemy(3337, 624);
        addEnemy(3770, 560);
        addEnemy(4256, 400);
        addEnemy(4622, 720);
        addEnemy(5063, 720);
        addEnemy(5554, 656);
        addEnemy(6179, 592);
        addEnemy(6407, 592);
        addEnemy(6808, 656);
        addEnemy(7349, 720);
    }

    public void addEnemy(double enemyX, double enemyY){
        GoldDemon g = new GoldDemon(tileMap);
        g.setPosition(enemyX, enemyY);
        enemies.add(g);
    }

    @Override
    public void update() {
        player.update();
        background.update();
        tileMap.setPosition(Main.WIDTH - player.getX(), Main.HEIGHT - player.getY());

        if(enemies.size() <= 0){
            gsm.setState(GameStateManager.WINNERSTATE);
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
