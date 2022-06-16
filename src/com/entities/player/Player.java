package com.entities.player;

import com.TileMap.TileMap;
import com.entities.Animation;
import com.entities.MapEntity;
import com.entities.enemies.Enemy;
import com.entities.gameObjects.Arrow;
import com.sound.GameSound;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends MapEntity {

    //Player characteristics
    private int life;
    private int maxLife;
    private boolean dead;
    private boolean recDamage = false;
    private long damageTime;
    private int coins;

    //projectiles
    private boolean shootArrow;
    private int numArrows;
    private int maxNumArrows;
    private int arrowDamage;
    private ArrayList<Arrow> arrows;

    //Sword attack
    private boolean attack;
    private int attackDamage;
    private int attackRange;

    //Sounds
    private HashMap<String, GameSound> sound;

    //Animations
    private ArrayList<Sprite[]> sprites;
    private final int[] numFrames = {7, 7, 1, 1, 9, 9, 2, 2, 5, 6, 6, 13, 13};

    //character Actions
    public static final int JUMPRIGHT = 0;
    public static final int JUMPLEFT = 1;
    public static final int IDLERIGHT = 2;
    public static final int IDLELEFT = 3;
    public static final int WALKRIGHT = 4;
    public static final int WALKLEFT = 5;
    public static final int FALLINGRIGHT = 6;
    public static final int FALLINGLEFT = 7;
    public static final int CROUCHING = 8;
    public static final int ATTACKRIGHT = 9;
    public static final int ATTACKLEFT = 10;
    public static final int SHOOTRIGHT = 11;
    public static final int SHOOTLEFT = 12;

    /**Main Constructor for player*/
    public Player(TileMap tm) {
        super(tm);

        width = 64;
        height = 52;
        cWidth = 32;
        cHeight = 32;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        coins = 0;

        life = maxLife = 10;
        numArrows = maxNumArrows = 3;
        arrowDamage = 5;
        arrows = new ArrayList<Arrow>();

        attackDamage = 5;
        attackRange = 50;

        //load Sprites into arraylist
        Texture[] texture = new Texture[13];
        for (int i = 0; i < 13; i++) {
            texture[i] = new Texture();
        }

        try{
            //Loads image files into textures
            texture[JUMPRIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/JumpingRight.png"));
            texture[JUMPLEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/JumpingLeft.png"));
            texture[IDLERIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/IdleRight.png"));
            texture[IDLELEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/IdleLeft.png"));
            texture[WALKRIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/walkRight.png"));
            texture[WALKLEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/walkLeft.png"));
            texture[FALLINGRIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/FallRight.png"));
            texture[FALLINGLEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/FallLeft.png"));
            texture[CROUCHING].loadFromFile(Paths.get("src/com/resources/images/playerImages/Crouch.png"));
            texture[ATTACKRIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/AttackRight.png"));
            texture[ATTACKLEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/AttackLeft.png"));
            texture[SHOOTRIGHT].loadFromFile(Paths.get("src/com/resources/images/playerImages/ShootRight.png"));
            texture[SHOOTLEFT].loadFromFile(Paths.get("src/com/resources/images/playerImages/ShootLeft.png"));

            //Array list that will store sprites for all actions
            sprites = new ArrayList<Sprite[]>();

            //Loads the sprites into and array the the array is added to the array list
            for (int i = 0; i < 13; i++) {
                Sprite[] frames = new Sprite[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {
                    if(i != 8 && i != 9 && i != 10){
                        frames[j] = new Sprite(texture[i]);
                        frames[j].setTextureRect(new IntRect(j*width, 0, width, height));
                    }else if(i==9){
                        if(j==0){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(192, 0, width, height));
                        }else if(j==1){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(373, 0, width, height));
                        }else if(j==2){
                            frames[j] = new Sprite(texture[i]);
                                frames[j].setTextureRect(new IntRect(555, 0, width, height));
                        }else if(j==3){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(766, 0, width, height));
                        }else if(j==4){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(959, 0, 95, height));
                        }else if(j==5){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(1050, 0, width, height));
                        }
                    }else if(i==10){
                        if(j==0){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(215, 0, width, height));
                        }else if(j==1){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(409, 0, width, height));
                        }else if(j==2){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(595, 0, width, height));
                        }else if(j==3){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(731, 0, width, height));
                        }else if(j==4){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(927, 0, 95, height));
                        }else if(j==5){
                            frames[j] = new Sprite(texture[i]);
                            frames[j].setTextureRect(new IntRect(1016, 0, width, height));
                        }
                    }

                }
                sprites.add(frames);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //Loading hashmap
        sound = new HashMap<String, GameSound>();
        sound.put("walk", new GameSound("src/com/resources/sounds/footstepForest.wav"));
        sound.put("jump", new GameSound("src/com/resources/sounds/jump.wav"));
        sound.put("hit", new GameSound("src/com/resources/sounds/sowrdHit.wav"));
        sound.put("slash", new GameSound("src/com/resources/sounds/swordSlash.wav"));
        sound.put("shoot", new GameSound("src/com/resources/sounds/bow.wav"));
        sound.put("cCoin", new GameSound("src/com/resources/sounds/coin.wav"));


        animation = new Animation();

        if(facingRight){
            currAction = IDLERIGHT;
            animation.setFrames(sprites.get(IDLERIGHT));
        }else{
            currAction = IDLELEFT;
            animation.setFrames(sprites.get(IDLELEFT));
        }
        animation.setDelay(400);

    }

    /*Returns the stats of the player*/
    public int getLife(){return life;}
    public int getMaxLife(){return maxLife;}
    public int getNumArrows(){return numArrows;}
    public int getMaxNumArrows(){return maxNumArrows;}

    public void setShooting(){
        shootArrow = true;
    }
    public void setAttack(){
        attack = true;
    }
    
    public boolean isDead(){
        if(life <= 0){
            life = 0;
            return true;
        }else{
            return false;
        }
    }

    /**Checks it player has hit or shot an enemy*/
    public void checkHit(ArrayList<Enemy> enemies){

        //Attack damage on enemies for ranged/melee attack
        for (Enemy enemy : enemies) {
            //melee attack
            if(attack){
                if(facingRight){
                    if (enemy.getX() > x && enemy.getX() < x + attackRange && enemy.getY() > y - height / 2 && enemy.getY() < y + height / 2) {
                        enemy.hit(attackDamage);
                        sound.get("hit").playSound();
                    }
                }else{
                    if (enemy.getX() < x && enemy.getX() > x - attackRange && enemy.getY() > y - height / 2 && enemy.getY() < y + height / 2) {
                        enemy.hit(attackDamage);
                        sound.get("hit").playSound();
                    }
                }
            }

            //ranged attack
            for (Arrow arrow : arrows) {
                if (arrow.intersects(enemy)) {
                    enemy.hit(arrowDamage);
                    arrow.setShot();
                }
            }

            //check if any of the skulls hit the player
            if (intersects(enemy))
            {
                hit(enemy.getAttackDamage());
            }
        }
    }

    /**Gets the next position of the player depending on the users input*/
    private void getNextPosition(){
        //Movement
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        }else{
            if(dx > 0){
                dx -= stopSpeed;
                if(dx < 0){
                    dx = 0;
                }
            }else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0){
                    dx = 0;
                }
            }
        }

        //Can't move and attack
        if((currAction == ATTACKRIGHT || currAction == SHOOTRIGHT || currAction == ATTACKLEFT || currAction == SHOOTLEFT) && !(jumping || falling)){ dx = 0; }

        //jumping
        if(jumping && !falling){
            dy = jumpStart;
            falling = true;
        }

        //falling
        if(falling){
            if(dy > 0){
                dy += fallSpeed;
            }
            else {
                dy += fallSpeed;
            }
            if(dy > 0){
                jumping = false;
            }

            if(dy < 0 && !jumping){
                dy += stopJumpSpeed;
            }

            if(dy > maxFallSpeed){
                dy = maxFallSpeed;
            }
        }
    }


    public void hit(int damage)
    {
        if(!attack){
            if (recDamage)
            {
                return;
            }
            life = life - damage;
            if (life <= 0)
            {
                dead = true;
            }
            recDamage = true;
            damageTime = System.nanoTime();
        }

    }

    public void collectCoin(){
        coins += 1;
        sound.get("cCoin").playSound();
        if(coins % 10 == 0) {
            life++;
        }
    }

    public int getCoins(){
        return coins;
    }

    /**Periodically updates status/appearance/position of player*/
    public void update(){

        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);
        //Set sounds
        if(currAction == WALKRIGHT || currAction == WALKLEFT){
            sound.get("walk").playSound();
        }
        else if(currAction == JUMPRIGHT || currAction == JUMPLEFT){
            sound.get("jump").playSound();
        }

        //Check if attack is over
        if(currAction == ATTACKRIGHT || currAction == ATTACKLEFT){
            if(animation.isPlayedOnce()){
                attack = false;
            }
        }

        //Releasing arrow
        numArrows += 1;
        if(numArrows > maxNumArrows){
            numArrows = maxNumArrows;
        }
        if(currAction == SHOOTRIGHT || currAction == SHOOTLEFT){
            if(animation.isPlayedOnce()){
                shootArrow = false;
                if(numArrows > 1){
                    numArrows -= 1;
                    Arrow arrow = new Arrow(tileMap, facingRight);
                    arrow.setPosition(x, y);
                    sound.get("shoot").playSound();
                    arrows.add(arrow);
                }
            }
        }
        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).update();
            if(arrows.get(i).isRemove()){
                arrows.remove(i);
                i--;
            }

        }

        //Set Direction
        if(currAction != ATTACKRIGHT && currAction != ATTACKLEFT && currAction != SHOOTRIGHT && currAction != SHOOTLEFT){
            if(right){facingRight = true;}
            if(left){facingRight = false;}
        }

        //Set if a Recdmg is done to prohibit invicible forever
        if (recDamage)
        {
            long damagedTimer = (System.nanoTime() - damageTime)/1000000;
            if (damagedTimer > 1000)
            {
                recDamage = false;
            }
        }

        //set animation
        if(attack){
            if(currAction != ATTACKRIGHT && currAction != ATTACKLEFT){
                if(facingRight){
                    currAction = ATTACKRIGHT;
                    animation.setFrames(sprites.get(ATTACKRIGHT));
                }else{
                    currAction = ATTACKLEFT;
                    animation.setFrames(sprites.get(ATTACKLEFT));
                }
                sound.get("slash").playSound();
                animation.setDelay(50);
                width = 50;
            }
        }else if(shootArrow){
            if(currAction != SHOOTRIGHT && currAction != SHOOTLEFT){
                if(facingRight){
                    currAction = SHOOTRIGHT;
                    animation.setFrames(sprites.get(SHOOTRIGHT));
                }else{
                    currAction = SHOOTLEFT;
                    animation.setFrames(sprites.get(SHOOTLEFT));
                }
                animation.setDelay(100);
                width = 30;

            }
        }else if(dy>0){
            if(currAction != FALLINGRIGHT && currAction != FALLINGLEFT){
                if(facingRight){
                    currAction = FALLINGRIGHT;
                    animation.setFrames(sprites.get(FALLINGRIGHT));
                }else{
                    currAction = FALLINGLEFT;
                    animation.setFrames(sprites.get(FALLINGLEFT));
                }
                animation.setDelay(100);
                width = 30;
            }
        }else if(dy < 0){
            if(currAction != JUMPRIGHT && currAction != JUMPLEFT){
                if(facingRight){
                    currAction = JUMPRIGHT;
                    animation.setFrames(sprites.get(JUMPRIGHT));
                }else{
                    currAction = JUMPLEFT;
                    animation.setFrames(sprites.get(JUMPLEFT));
                }
                animation.setDelay(-1);
                width = 30;

            }
        }else if(left || right){
            if(currAction != WALKRIGHT && currAction != WALKLEFT){
                if(facingRight){
                    currAction = WALKRIGHT;
                    animation.setFrames(sprites.get(WALKRIGHT));
                }else{
                    currAction = WALKLEFT;
                    animation.setFrames(sprites.get(WALKLEFT));
                }
                animation.setDelay(40);
                width = 30;

            }
        }else{
            if(currAction != IDLERIGHT && currAction != IDLELEFT){
                if(facingRight){
                    currAction = IDLERIGHT;
                    animation.setFrames(sprites.get(IDLERIGHT));
                }else{
                    currAction = IDLELEFT;
                    animation.setFrames(sprites.get(IDLELEFT));
                }
                animation.setDelay(400);
                width = 30;

            }
        }

        //Updates the sprites to be displayed
        animation.update();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
    }

    /**Draws the character onto the screen*/
    public void draw(RenderWindow window){
        setMapPosition();

        //draws the arrows from the player
        for (Arrow arrow : arrows) {
            arrow.draw(window);
        }

        //when the player is hit the player will flash for a while
        if(recDamage){
            long elapsed = (System.nanoTime() - damageTime) / 1000000;
            if(elapsed / 100 % 2 == 0){
                return;
            }
        }

        //draws the players position which is constantly changing during gameplay
        animation.getSprite().setPosition((float)(x + xMap - width / 2 + width - 40), (float)((y + yMap - height / 2) - 8));
        window.draw(animation.getSprite());

    }
}
