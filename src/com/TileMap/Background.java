package com.TileMap;

import com.main.Main;
import org.jsfml.graphics.*;

import java.nio.file.Paths;

/**Creates and manipulates background properties and sets dimensions of background*/
public class Background {
    
    private Texture texture;
    private Sprite sprite;
    private double x, y, dx, dy, moveScale;

    /**Constructor for background*/
    public Background(String s, double ms){
        try {
            texture = new Texture();
            texture.loadFromFile(Paths.get(s));
            this.moveScale = ms;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**Sets position of background*/
    public void setPosition(double x, double y){
        this.x = (x * moveScale) % Main.WIDTH;
        this.y = (y * moveScale) % Main.HEIGHT;
    }

    /**Sets background vectors*/
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    /**Updates position of background*/
    public void update(){
        x += dx;
        y += dy;
    }

    /**Draws background image onto the main window*/
    public void draw(RenderWindow window){
        sprite = new Sprite(texture);
        if(x < 0){
            window.draw(sprite);
            sprite.scale((float) (x + Main.WIDTH), (float) y);
        }else if(x > 0){
            window.draw(sprite);
            sprite.scale((float) (x - Main.WIDTH), (float) y);
        }
    }
}