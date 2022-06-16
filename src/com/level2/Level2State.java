package com.level2;

import com.GameState.GameStateManager;
import com.GameState.GameState;
import com.TileMap.Background;
import com.TileMap.TileMap;
import com.entities.PlayerStats;
import com.entities.enemies.Enemy;
import com.entities.enemies.Orc;
import com.entities.gameObjects.Blast;
import com.entities.gameObjects.Coin;
import com.entities.player.Player;
import com.main.Main;
import com.sound.GameSound;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

public class Level2State extends GameState {

    private TileMap tileMap;
    private Player player;
    private Background background;
    private GameSound backgroundSound;
    private ArrayList<Enemy> enemies;
    private ArrayList<Blast> blasts;
    private ArrayList<Coin> coins;
    private ArrayList<String> sounds;
    private PlayerStats playerStats;

    public Level2State(GameStateManager gsm){
        this.gsm = gsm;

        try{
            background = new Background("src/com/resources/images/level2Images/level2BG.png", 1);
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
        tileMap.loadTiles("src/com/resources/images/otherLevelTileset.png");
        tileMap.loadMap("src/com/resources/images/level2Images/level2Map.txt");
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
        addCoin(400, 720);
        addCoin(548, 592);
        addCoin(902, 720);
        addCoin(1140, 656);
        addCoin(1395, 496);
        addCoin(1638, 720);
        addCoin(2214, 720);
        addCoin(1709, 464);
        addCoin(2078, 400);
        addCoin(1639, 272);
        addCoin(2191, 176);
        addCoin(2466, 240);
        addCoin(1952, 304);
        addCoin(2783, 272);
        addCoin(3235, 272);
        addCoin(3613, 176);
        addCoin(3847, 144);
        addCoin(4085, 432);
        addCoin(3862, 496);
        addCoin(4074, 720);
        addCoin(4642, 720);
        addCoin(5125, 720);
        addCoin(5897, 720);
        addCoin(6329, 560);
        addCoin(7527, 624);
        addCoin(6900, 400);
        addCoin(7450, 304);
    }

    /**loads the enemies into the game*/
    public void loadEnemies(){
        enemies = new ArrayList<Enemy>();
        addEnemy(462, 120);
        addEnemy(902, 720);
        addEnemy(1140, 656);
        addEnemy(1638, 720);
        addEnemy(2214, 720);
        addEnemy(2078, 400);
        addEnemy(1639, 272);
        addEnemy(2191, 176);
        addEnemy(3613, 176);
        addEnemy(4642, 720);
        addEnemy(3862, 496);
        addEnemy(5125, 720);
        addEnemy(5897, 720);
        addEnemy(6329, 560);
        addEnemy(6899, 400);
        addEnemy(7314, 336);
        addEnemy(7852, 624);
    }

    public void addEnemy(double enemyX, double enemyY){
        Orc c = new Orc(tileMap);
        c.setPosition(enemyX, enemyY);
        enemies.add(c);
    }

    @Override
    public void update() {
        player.update();
        background.update();
        tileMap.setPosition(Main.WIDTH - player.getX(), Main.HEIGHT - player.getY());

        if(enemies.size() <= 0){
            gsm.setState(GameStateManager.LEVEL3STATE);
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
