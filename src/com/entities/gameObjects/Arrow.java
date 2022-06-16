package com.entities.gameObjects;

import com.TileMap.TileMap;
import com.entities.Animation;
import com.entities.MapEntity;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Arrow extends MapEntity {

    private boolean shot;
    private boolean remove;
    private Sprite[] shootSprite = new Sprite[1];
    private Sprite[] hitSprite = new Sprite[4];
    private Texture[] texture = new Texture[2];

    private static final int HITSPRITE = 0;
    private static final int SHOTSPRITE = 1;




    public Arrow(TileMap tm, boolean right) {
        super(tm);

        moveSpeed = 3.8;
        if (right){
            dx = moveSpeed;
        }else{
            dx = -moveSpeed;
        }

        width = 30;
        height = 30;
        cWidth = 14;
        cHeight = 14;


        //loading sprite
        try{
            texture[HITSPRITE] = new Texture();
            texture[HITSPRITE].loadFromFile(Paths.get("src/com/resources/images/gameObjects/impact.png"));

            texture[SHOTSPRITE] = new Texture();
            if(right) {
                texture[SHOTSPRITE].loadFromFile(Paths.get("src/com/resources/images/gameObjects/arrowRight.png"));
            }else{
                texture[SHOTSPRITE].loadFromFile(Paths.get("src/com/resources/images/gameObjects/arrowLeft.png"));
            }

            shootSprite[0] = new Sprite(texture[SHOTSPRITE]);

            for (int i = 0; i < hitSprite.length; i++) {
                hitSprite[i] = new Sprite(texture[HITSPRITE]);
                hitSprite[i].setTextureRect(new IntRect(i*115, 0, 115, 83));

            }

            animation = new Animation();
            animation.setFrames(shootSprite);
            animation.setDelay(70);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setShot(){
        if(shot){
            return;
        }
        shot = true;
        animation.setFrames(hitSprite);
        animation.setDelay(70);
        dx = 0;

    }

    public boolean isRemove(){
        return remove;
    }

    public void update(){
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        if(dx == 0 && !shot){
            setShot();

        }

        animation.update();
        if(shot && animation.isPlayedOnce()){
            remove = true;
        }

    }

    public void draw(RenderWindow window){
        setMapPosition();
        animation.getSprite().setPosition((float)(x + xMap - width / 2 + width - 40), (float)(y + yMap - height / 2));
        window.draw(animation.getSprite());


    }


}
