package com.entities;

import com.TileMap.TileMap;
import com.TileMap.Tiles;
import com.main.Main;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;

public abstract class MapEntity {

    //Tilemap properties
    protected TileMap tileMap;
    protected int tileSize;
    protected double xMap, yMap;

    //positions/vectors
    protected double x, y, dy, dx;

    //dimensions
    protected int width, height;

    //collision/hit box
    protected int cWidth, cHeight;

    //collision detection
    protected int currRow, currCol;
    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    //xDest/yDest = entity destination, xTemp/yTemp = temporary entity vector values
    protected double xDest, yDest, xTemp, yTemp;

    //animation
    protected int currAction, prevAction;
    protected Animation animation;
    protected boolean facingRight;

    //movement
    protected boolean left, right, up, down, jumping, falling;

    //movement attributes
    protected double moveSpeed, maxSpeed, stopSpeed, fallSpeed, maxFallSpeed, jumpStart, stopJumpSpeed;

    //constructor of MapEntity
    public MapEntity(TileMap tm){
        tileMap = tm;
        tileSize = tm.getTileSize();
    }

    //Checks whether two rectangle objects collide (hit box)
    public boolean intersects(MapEntity mE){
        FloatRect r1 = getRectangle();
        FloatRect r2 = mE.getRectangle();
        return r1.intersection(r2) != null;
    }

    public FloatRect getRectangle(){
        return new FloatRect((int)x-cWidth, (int)y-cHeight, cWidth, cHeight);
    }

    //checks tile types of tiles surrounding player, either blocked or normal
    public void calcCorners(double x, double y){

        int leftTile = (int)(x - (cWidth / 2)) / tileSize;
        int rightTile = (int)(x + (cWidth / 2) - 1) / tileSize;
        int topTile = (int)(y - (cHeight / 2)) / tileSize;
        int bottomTile = (int)(y + (cHeight / 2) - 1) / tileSize;

        int tL = tileMap.getType(topTile, leftTile);
        int tR = tileMap.getType(topTile, rightTile);
        int bL = tileMap.getType(bottomTile, leftTile);
        int bR = tileMap.getType(bottomTile, rightTile);


        //should return either true or false if the listed tiles are blocked or not
        topLeft = (tL == Tiles.BLOCKED);
        topRight = (tR == Tiles.BLOCKED);
        bottomLeft = (bL == Tiles.BLOCKED);
        bottomRight = (bR == Tiles.BLOCKED);

    }

    //Collision detection of map entity and tile map
    public void checkTileMapCollision(){
        currCol = (int)x/tileSize;
        currRow = (int)y/tileSize;

        xDest = x + dx;
        yDest = y + dy;

        xTemp = x;
        yTemp = y;

        //Checks Tile collision in y direction
        calcCorners(x, yDest);
        if(dy < 0){
            if(topLeft || topRight){
                dy = 0;
                yTemp = currRow * tileSize + (float)(cHeight / 2);
            }else{
                yTemp += dy;
            }
        }

        if(dy > 0){
            if(bottomLeft || bottomRight){
                dy = 0;
                falling = false;
                yTemp = (currRow + 1) * tileSize - cHeight / 2;
            }else{
                yTemp += dy;
            }
        }

        //Checks tile collision in x direction
        calcCorners(xDest, y);
        if(dx < 0){
            if(topLeft || bottomLeft){
                dx = 0;
                xTemp = currCol * tileSize + cWidth / 2;
            }else{
                xTemp += dx;
            }
        }

        if(dx > 0){
            if(topRight || bottomRight){
                dx = 0;
                xTemp = (currCol + 1) * tileSize - cWidth / 2;
            }else{
                xTemp += dx;
            }  
        }

        //Checks if the player is still walking on solid ground, if not it should fall

        if(!falling){
            calcCorners(x, yDest + 1);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
       }
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getcWidth() {
        return cWidth;
    }

    public int getcHeight() {
        return cHeight;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    //Sets tilemap position relative to the player
    public void setMapPosition(){
        xMap = tileMap.getXPosition();
        yMap = tileMap.getYPosition();
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    //Sets bounds for objects of screen
    public boolean notOnScreen(){
        return x + xMap + width < 0 ||
                x + xMap - width > Main.WIDTH * Main.SCALE||
                y + yMap + height < 0 ||
                y + yMap - height > Main.HEIGHT * Main.SCALE;
    }

    public void draw(RenderWindow window)
    {
        window.draw(animation.getSprite());
        if (facingRight) {
            window.draw(animation.getSprite());
        }
        else {
            
        }
    }
}
