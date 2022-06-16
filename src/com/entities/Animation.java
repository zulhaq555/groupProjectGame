package com.entities;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Animation {

    private Texture texture;
    private Sprite[] sprite;

    private int currentSprite;

    private long startTime;
    private long delay;
    private long endTime;

    private boolean playedOnce;

    public Animation(){
        playedOnce = false;
    }

    public void setFrames(Sprite[] frames){
        sprite = frames;
        currentSprite = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long delayTime){
        delay = delayTime;
    }

    public void setFrame(int i){
        currentSprite = i;
    }

    public void update(){
        if(delay == -1){
            return;
        }

        long elapsedTime = (System.nanoTime() - startTime) / 1000000;
        if (elapsedTime > delay){
            currentSprite++;
            startTime = System.nanoTime();
        }

        if(currentSprite == sprite.length){
            currentSprite = 0;
            playedOnce = true;
        }
    }

    public int getCurrentSprite(){return currentSprite;}

    public Sprite getSprite(){
        return sprite[currentSprite];
    }

    public boolean isPlayedOnce(){return playedOnce;}

}
