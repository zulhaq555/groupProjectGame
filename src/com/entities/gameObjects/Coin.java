package com.entities.gameObjects;

import com.TileMap.TileMap;
import com.entities.Animation;
import com.entities.MapEntity;
import com.entities.player.Player;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.nio.file.Paths;

public class Coin extends MapEntity {
    private static int totalCollected = 0;
    private boolean isTaken = false;
    private boolean remove;
    private Sprite[] coinSprite = new Sprite[6];
    private Texture[] texture = new Texture[1];

    private static final int COIN = 0;

    public Coin(TileMap tm) {
        super(tm);

        width = 36;
        height = 32;
        cWidth = 36;
        cHeight = 32;


        //loading sprite
        try{
            texture[COIN] = new Texture();
            texture[COIN].loadFromFile(Paths.get("src/com/resources/images/gameObjects/Coins.png"));

            for (int i = 0; i < coinSprite.length; i++) {
                coinSprite[i] = new Sprite(texture[COIN]);
                coinSprite[i].setTextureRect(new IntRect(i*36, 0, 36, 32));

            }
            animation = new Animation();
            animation.setFrames(coinSprite);
            animation.setDelay(70);

        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        animation.update();
    }

    public void checkHit(Player p) {
        if(!isTaken) {
            int pX = p.getX();
            int pY = p.getY();

            if ((pX >= x - 25 && pX <= x + 20)) {
                if ((pY >= y - 20 && pY <= y + 20)) {
                    isTaken = true;
                    totalCollected++;
                    p.collectCoin();
                }
            }
        }
    }

    public void draw(RenderWindow window){
        if(!isTaken) {
            setMapPosition();
            animation.getSprite().setPosition((float) (x + xMap - width / 2 + width - 40), (float) (y + yMap - height / 2));
            window.draw(animation.getSprite());
        }
    }


}
