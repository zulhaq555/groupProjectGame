package com.TileMap;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public class Tiles {
    private Sprite image;
    private int type;

    //tile types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    public Tiles(Sprite image, int type)
    {
        this.image = image;
        this.type = type;
    }

    public void draw(RenderWindow window, Sprite sprite, int x, int y){
        window.draw(sprite);
        sprite.setPosition((float)x, (float)y);
    }

    public Sprite getImage() { return image; }
    public int getType() { return type; }
}