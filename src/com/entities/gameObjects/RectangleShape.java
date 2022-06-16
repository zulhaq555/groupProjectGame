package com.entities.gameObjects;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

public class RectangleShape {
    public org.jsfml.graphics.RectangleShape rectangle;

    public RectangleShape(float x, float y, float xPos, float yPos){
        rectangle = new org.jsfml.graphics.RectangleShape(new Vector2f(x, y));
        rectangle.setOrigin(x/2, y/2);
        rectangle.setPosition(xPos, yPos);
    }

    public void draw(RenderWindow window){
        window.draw(rectangle);
    }
}
