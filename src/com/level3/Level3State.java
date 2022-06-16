package com.level3;

import com.GameState.GameState;
import com.GameState.GameStateManager;
import com.TileMap.Background;
import com.TileMap.TileMap;
import com.entities.PlayerStats;
import com.entities.enemies.Enemy;
import com.entities.enemies.Vampire;
import com.entities.gameObjects.Blast;
import com.entities.gameObjects.Coin;
import com.entities.player.Player;
import com.main.Main;
import com.sound.GameSound;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;

public class Level3State extends GameState {
    private TileMap tileMap;
    private Player player;
    private Background background;
    private GameSound backgroundSound;
    private ArrayList<Enemy> enemies;
    private ArrayList<Blast> blasts;
    private ArrayList<Coin> coins;
    private ArrayList<String> sounds;
    private PlayerStats playerStats;

    public Level3State(GameStateManager gsm){
        this.gsm = gsm;

        try{
            background = new Background("src/com/resources/images/level3Images/level3BG.png", 1);
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
        tileMap.loadTiles("src/com/resources/images/otherLevelTileset.png");
        tileMap.loadMap("src/com/resources/images/level3Images/level3Map.txt");
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
        addCoin(523,720);
        addCoin(656, 592);
        addCoin(1152, 720);
        addCoin(1364, 720);
        addCoin(2138, 720);
        addCoin(1088, 528);
        addCoin(1616, 560);
        addCoin(2442, 496);
        addCoin(2864, 496);
        addCoin(3332, 720);
        addCoin(3775, 720);
        addCoin(4167, 720);
        addCoin(4624, 720);
        addCoin(3739, 560);
        addCoin(3984, 560);
        addCoin(3696, 336);
        addCoin(3504, 336);
        addCoin(4326, 336);
        addCoin(4592, 336);
        addCoin(5034, 496);
        addCoin(5571, 720);
        addCoin(5866, 496);
        addCoin(6139, 720);
        addCoin(6544, 720);
        addCoin(6768, 560);
        addCoin(7120, 464);
        addCoin(7943, 720);
        addCoin(7792, 720);
        addCoin(6864, 464);
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
        addEnemy(523,720);
        addEnemy(656, 592);
        addEnemy(1152, 720);
        addEnemy(1364, 720);
        addEnemy(2138, 720);
        addEnemy(1088, 528);
        addEnemy(1616, 560);
        addEnemy(2442, 496);
        addEnemy(2864, 496);
        addEnemy(3332, 720);
        addEnemy(3775, 720);
        addEnemy(4167, 720);
        addEnemy(4624, 720);
        addEnemy(3739, 560);
        addEnemy(3984, 560);
        addEnemy(3696, 336);
        addEnemy(3504, 336);
        addEnemy(4326, 336);
        addEnemy(4592, 336);
        addEnemy(5034, 496);
        addEnemy(5571, 720);
        addEnemy(5866, 496);
        addEnemy(6139, 720);
        addEnemy(6544, 720);
        addEnemy(6768, 560);
        addEnemy(7120, 464);
        addEnemy(7943, 720);
        addEnemy(7792, 720);
        addEnemy(6864, 464);

    }

    public void addEnemy(double enemyX, double enemyY){
        Vampire s = new Vampire(tileMap);
        s.setPosition(enemyX, enemyY);
        enemies.add(s);
    }

    @Override
    public void update() {
        player.update();
        background.update();
        tileMap.setPosition(Main.WIDTH - player.getX(), Main.HEIGHT - player.getY());

        if(enemies.size() <= 0){
            gsm.setState(GameStateManager.LEVEL4STATE);
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
