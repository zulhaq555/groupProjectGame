package com.entities.enemies;

import com.TileMap.TileMap;
import com.entities.Animation;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Orc extends Enemy{
    private ArrayList<Sprite[]> sprites;
    private final int[] numFrames = {9, 9};

    public static final int WALKRIGHT = 0;
    public static final int WALKLEFT = 1;

    public Orc(TileMap tm) {
        super(tm);
        moveSpeed = 0.5;
        maxSpeed = 1.0;
        fallSpeed = 1.0;
        maxFallSpeed = 10.0;

        width = 64;
        height = 42;
        cWidth = 32;
        cHeight = 32;

        health = maxHealth = 10;
        attackDamage = 1;

        left = true;

        //load Sprites into arraylist
        Texture[] texture = new Texture[2];
        for (int i = 0; i < 2; i++) {
            texture[i] = new Texture();
        }

        try{
            //Loads image files into textures
            texture[WALKRIGHT].loadFromFile(Paths.get("src/com/resources/images/enemies/orcSprites/orcRight.png"));
            texture[WALKLEFT].loadFromFile(Paths.get("src/com/resources/images/enemies/orcSprites/orcLeft.png"));


            //Array list that will store sprites for all actions
            sprites = new ArrayList<Sprite[]>();

            //Loads the sprites into and array the the array is added to the array list
            for (int i = 0; i < 2; i++) {
                Sprite[] frames = new Sprite[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {
                    frames[j] = new Sprite(texture[i]);
                    frames[j].setTextureRect(new IntRect(j*width, 0, width, height));
                }
                sprites.add(frames);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        animation = new Animation();

        if(facingRight){
            currAction = WALKRIGHT;
            animation.setFrames(sprites.get(WALKRIGHT));
        }else{
            currAction = WALKLEFT;
            animation.setFrames(sprites.get(WALKLEFT));
        }
        animation.setDelay(60);
    }

    private void getNextPosition(){
        //Movement
        if(left){

            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }

        }else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if(falling){
            dy += fallSpeed;
        }
    }

    @Override
    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        if(recDamage){
            long elapsed = (System.nanoTime() - damageTimer) / 1000000;
            if(elapsed > 400){
                recDamage = false;
            }
        }

        if(right && dx ==0){
            right = false;
            left = true;
            currAction = WALKLEFT;
            animation.setFrames(sprites.get(WALKLEFT));
        }else if(left && dx ==0){
            right = true;
            left = false;
            currAction = WALKRIGHT;
            animation.setFrames(sprites.get(WALKRIGHT));
        }
        animation.setDelay(40);

        animation.update();
    }

    @Override
    public void draw(RenderWindow window) {
        setMapPosition();
        animation.getSprite().setPosition((float)(x + xMap - width / 2 + width - 40), (float)((y + yMap - height / 2) - 8));
        window.draw(animation.getSprite());

    }
}
