package com.entities.gameObjects;

import com.entities.Animation;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Blast {

    private int x, y, xMap, yMap;
    private int width, height;

    private Animation animation;
    private Texture texture;
    private Sprite[] sprite = new Sprite[6];

    public boolean finished;

    public Blast(int x, int y){

        this.x = x;
        this.y = y;

        width = 135;
        height = 145;

        try{
            texture = new Texture();
            texture.loadFromFile(Paths.get("src/com/resources/images/enemies/enemyDeath.png"));

            for (int i = 0; i < sprite.length; i++) {
                sprite[i] = new Sprite(texture);
                sprite[i].setTextureRect(new IntRect(i*width, 0, width, height));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprite);
        animation.setDelay(70);
    }

    public void update(){
        animation.update();
        if(animation.isPlayedOnce()){
            finished = true;
        }
    }

    public boolean isFinished(){
        return finished;
    }

    public void setMapPosition(int x, int y){
        xMap = x;
        yMap = y;
    }

    public void draw(RenderWindow window){
        animation.getSprite().setPosition((float)(x + xMap - width / 2 + width - 40), (float)((y + yMap - height / 2) - 8));
        window.draw(animation.getSprite());
    }
}
